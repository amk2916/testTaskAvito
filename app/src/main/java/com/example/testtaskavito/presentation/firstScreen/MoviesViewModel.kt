package com.example.testtaskavito.presentation.firstScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.testtaskavito.domain.GetMoviesUseCase
import com.example.testtaskavito.domain.MovieForList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val queryGetMoviesUseCaseProvider: GetMoviesUseCase
) : ViewModel() {

    //private val _query = MutableStateFlow("")
    //val query: StateFlow<String> = _query.asStateFlow()

    private var newPagingSource: PagingSource<*, *>? = null

    val movies: StateFlow<PagingData<MovieForList>> =
        newPager().flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(): Pager<Int, MovieForList> {
        return Pager(PagingConfig(5, enablePlaceholders = false)) {
            val queryNewsUseCase = queryGetMoviesUseCaseProvider.getMovies()
            queryNewsUseCase.also {
                newPagingSource = it
            }
        }
    }
}