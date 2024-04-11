package com.example.testtaskavito.presentation.firstScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.domain.GetMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        queryGetMoviesUseCaseProvider.getMovies()
            .cachedIn(viewModelScope)
            .onEach { pagingData ->
                _movies.value = pagingData
            }.launchIn(viewModelScope)

    }

}