package com.example.testtaskavito.presentation.firstScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.domain.GetMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

data class Filter(
    val nameCountry: String?,
    val ageRating: Int?,
    val year: Int?
) {
    fun isNotEmpty() = !nameCountry.isNullOrBlank() || ageRating != null || year != null
}

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MoviesViewModel @Inject constructor(
    private val queryGetMoviesUseCaseProvider: GetMoviesUseCase
) : ViewModel() {

    private val _movies: MutableStateFlow<PagingData<ModelForListLocal>> =
        MutableStateFlow(PagingData.empty())
    val movies: StateFlow<PagingData<ModelForListLocal>> = _movies


    private var _search: MutableStateFlow<PagingData<ModelForListLocal>> =
        MutableStateFlow(PagingData.empty())

    private val searchFlow =
        MutableSharedFlow<String>(1, 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    private val filterFlow =
        MutableSharedFlow<Filter>(0, 1, onBufferOverflow = BufferOverflow.DROP_LATEST)


    init {
        val searchFlow = searchFlow
            .distinctUntilChanged()
            .debounce(1_000)
            .shareIn(viewModelScope, SharingStarted.Lazily, 0)

        val searchPAge = searchFlow
            .filter { it.isNotBlank() }
            .map { createSeachFlow(it) }


        val mainPage = merge(
            flowOf(Unit),
            searchFlow.filter { it.isBlank() },
            filterFlow.filter { !it.isNotEmpty() }
        ).map { createFetchFlow() }

        val filterPage = filterFlow
            .filter { it.isNotEmpty() }
            .map {
                it.run {
                    createFetchFlow(
                        nameCountry = nameCountry,
                        year = year,
                        ageRating = ageRating
                    )
                }
            }

        merge(searchPAge, mainPage, filterPage)
            .flatMapLatest { it }
            .cachedIn(viewModelScope)
            .onEach { _movies.emit(it) }
            .launchIn(viewModelScope)
    }

    fun getWithFilter(filter: Filter) {
        filterFlow.tryEmit(filter)
    }

    fun search(query: String) {
        searchFlow.tryEmit(query)
    }

    fun createSeachFlow(query: String) = newPagerSearch(query).flow
        .onEach { pagingData -> _search.value = pagingData }

    //   fun reinit() = fetchMovies()
    private fun createPagingConfig() = PagingConfig(10, enablePlaceholders = true)
    private fun newPagerSearch(query: String?): Pager<Int, ModelForListLocal> {
        return Pager(createPagingConfig()) {
            queryGetMoviesUseCaseProvider.getMovieByName(query, true)
        }
    }


    private fun createFetchFlow(
        nameCountry: String? = null,
        year: Int? = null,
        ageRating: Int? = null
    ) = queryGetMoviesUseCaseProvider.getMovies(nameCountry, year, ageRating)

}