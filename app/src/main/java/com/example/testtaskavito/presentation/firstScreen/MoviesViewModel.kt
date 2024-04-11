package com.example.testtaskavito.presentation.firstScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.domain.GetMoviesUseCase
import com.example.testtaskavito.domain.MovieForList
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val queryGetMoviesUseCaseProvider: GetMoviesUseCase
) : ViewModel() {

    private val _movies: MutableStateFlow<PagingData<ModelForListLocal>> =
        MutableStateFlow(PagingData.empty())
    val movies: StateFlow<PagingData<ModelForListLocal>> = _movies

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        queryGetMoviesUseCaseProvider
            .getMovies()
            .onEach { pagingData ->
                _movies.value = pagingData
            }.launchIn(viewModelScope)

    }

}