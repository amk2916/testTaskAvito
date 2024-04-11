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

    fun getWithFilter(nameCountry: String?, ageRating: Int?, year: Int?){
        if (!(nameCountry ==null && ageRating==null && year ==null)){
            Log.e("getWithFilter", "fetchMovies")
            fetchMovies(nameCountry, ageRating, year)
        }
    }

    private fun fetchMovies(nameCountry: String? = null, ageRating: Int? = null, year: Int? = null) {
        queryGetMoviesUseCaseProvider.getMovies(nameCountry, year, ageRating)
            .cachedIn(viewModelScope)
            .onEach { pagingData ->
                _movies.value = pagingData
            }.launchIn(viewModelScope)

    }

}