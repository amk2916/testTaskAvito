package com.example.testtaskavito.presentation.firstScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.domain.GetMoviesUseCase
import com.example.testtaskavito.domain.Review
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MoviesViewModel @Inject constructor(
    private val queryGetMoviesUseCaseProvider: GetMoviesUseCase
) : ViewModel() {

    private val _movies: MutableStateFlow<PagingData<ModelForListLocal>> =
        MutableStateFlow(PagingData.empty())
    val movies: StateFlow<PagingData<ModelForListLocal>> = _movies


    private var _search: MutableStateFlow<PagingData<ModelForListLocal>> =
        MutableStateFlow(PagingData.empty())
    val search: StateFlow<PagingData<ModelForListLocal>> = _search

//    val seach: StateFlow<PagingData<ModelForListLocal>> = _movies

    private val searchFlow = MutableSharedFlow<String>(1, 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    init {
        fetchMovies()

        searchFlow
            .debounce(1_000)
            .distinctUntilChanged()
            .flatMapLatest {
                createSeachFlow(it)
            }
            .onEach {
                _movies.emit(it)
            }
            .launchIn(viewModelScope)
    }

    private var mainJob: Job? = null

    fun getWithFilter(nameCountry: String?, ageRating: Int?, year: Int?){
        if (!(nameCountry ==null && ageRating==null && year ==null)){
            Log.e("getWithFilter", "fetchMovies")
            fetchMovies(nameCountry, ageRating, year)
            mainJob?.cancel()
        }
    }

    fun search(query: String) {
        searchFlow.tryEmit(query)
    }

    fun createSeachFlow(query: String) =
        newPagerSearch(query)
            .flow
            .cachedIn(viewModelScope)
            .onEach { pagingData ->
                _search.value = pagingData
        }

 //   fun reinit() = fetchMovies()
    private fun createPagingConfig() = PagingConfig(10, enablePlaceholders = true)
    private fun newPagerSearch(query: String?): Pager<Int, ModelForListLocal> {
        return Pager(createPagingConfig()) {
            queryGetMoviesUseCaseProvider.getMovieByName(query, true)
        }
    }


    private fun fetchMovies(nameCountry: String? = null, ageRating: Int? = null, year: Int? = null) {
        mainJob = queryGetMoviesUseCaseProvider.getMovies(nameCountry, year, ageRating)
            .cachedIn(viewModelScope)
            .onEach { pagingData ->
                Log.e("fetchMovies", "fetchMovies")
                _movies.value = pagingData
            }
            .launchIn(viewModelScope)
    }

}