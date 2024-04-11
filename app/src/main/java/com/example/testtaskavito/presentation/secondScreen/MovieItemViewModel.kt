package com.example.testtaskavito.presentation.secondScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.testtaskavito.domain.GetMoviesUseCase
import com.example.testtaskavito.domain.Movie
import kotlinx.coroutines.flow.MutableSharedFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieItemViewModel @Inject constructor(
    private val queryGetMoviesUseCaseProvider: GetMoviesUseCase
) : ViewModel(){

    val movie = MutableSharedFlow<Movie>(0)

    val isLoadingFlow = MutableSharedFlow<Boolean>(0)
    fun getMovie(id: Int){
        viewModelScope.launch {
            delay(2000)
            val movie1 =  queryGetMoviesUseCaseProvider.getMovieForID(id)
            Log.e("emit", movie1.toString())
            isLoadingFlow.emit(false)
            movie.emit(
               movie1!!
            )
        }
    }
}