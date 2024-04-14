package com.example.testtaskavito.presentation.secondScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.testtaskavito.domain.GetMoviesUseCase
import com.example.testtaskavito.domain.Movie
import kotlinx.coroutines.flow.MutableSharedFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testtaskavito.data.Broacast
import com.example.testtaskavito.domain.Actor
import com.example.testtaskavito.domain.GetActorsUseCase
import com.example.testtaskavito.domain.GetReviewsUseCase
import com.example.testtaskavito.domain.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val queryGetMoviesUseCaseProvider: GetMoviesUseCase,
    private val getActorsUseCase: GetActorsUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
) : ViewModel() {
    private var idMovie = 0

    private val _review: MutableSharedFlow<PagingData<Review>> = MutableSharedFlow(
        1,
        1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val review: SharedFlow<PagingData<Review>> = _review.asSharedFlow()

    private val _actors: MutableSharedFlow<PagingData<Actor>> = MutableSharedFlow(
        1,
        1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    val actors: SharedFlow<PagingData<Actor>> = _actors.asSharedFlow()

    private fun start(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            newPagerActor(id)
                .flow
                .onEach {
                    Log.e("TEST", "start: $it ")
                    _actors.emit(it)
                }
                .launchIn(viewModelScope)

            newPagerReview(id)
                .flow
                .onEach {
                    Log.e("TAG", "start: $it ")
                    _review.emit(it)
                }
                .launchIn(viewModelScope)
        }
    }

    private fun newPagerActor(id: Int): Pager<Int, Actor> {
        return Pager(createPagingConfig()) {
            getActorsUseCase.getActorsForID(id)
        }
    }


    private fun newPagerReview(id: Int): Pager<Int, Review> {
        return Pager(createPagingConfig()) {
            getReviewsUseCase.getReviewForID(id)
        }
    }

    private fun createPagingConfig() = PagingConfig(10, enablePlaceholders = true)

    val movie = MutableSharedFlow<Movie>(0)

    val isLoadingFlow = MutableSharedFlow<Boolean>(0)
    val isErrorFlow = MutableSharedFlow<Boolean>(0)

    //TODO: все волшкбные числа/строки надо бы вынести в отдельные переменные
    fun getMovie(idMovie: Int) {
        this.idMovie = idMovie
        viewModelScope.launch {
            try {

                val movie1 = queryGetMoviesUseCaseProvider.getMovieForID(idMovie)
                if (movie1 == Movie.DEFAULT_MOVIE) {
                    Broacast.pushError("Загрузка не удалась")
                    isErrorFlow.emit(true)
                    isLoadingFlow.emit(false)

                } else {
                    start(idMovie)
                    Log.e("emit", movie1.toString())
                    isLoadingFlow.emit(false)
                    isErrorFlow.emit(false)
                    movie.emit(
                        movie1
                    )
                }
            } catch (ex: Exception) {
                isErrorFlow.emit(true)
                isLoadingFlow.emit(false)
                Broacast.pushError(ex.message.toString())
            }


        }
    }
}