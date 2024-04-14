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
) : ViewModel(){
    private var idMovie = 0


    private val _review: MutableSharedFlow<PagingData<Review>> = MutableSharedFlow(1, 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val review: SharedFlow<PagingData<Review>> = _review.asSharedFlow()
       //newPagerReview().flow.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val _actors: MutableStateFlow<PagingData<Actor>> = MutableStateFlow(PagingData.empty())
    val actors: StateFlow<PagingData<Actor>> = MutableStateFlow(PagingData.empty())
       // newPagerActor().flow.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun start(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            /*newPagerActor(id)
                .flow
                .collect{
                    Log.e("TEST" ,"${it.toString()}")
                    _actors.value = it }
              //  .collect { actors.value = it }
*/
            newPagerReview(id)
                .flow
                .cachedIn(viewModelScope)
                .onEach {
                    Log.e("TAG", "start: $it ", )
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
    fun getMovie(idMovie: Int){
        this.idMovie = idMovie
        viewModelScope.launch {
            val movie1 =  queryGetMoviesUseCaseProvider.getMovieForID(idMovie)
            Log.e("emit", movie1.toString())
            isLoadingFlow.emit(false)
            movie.emit(
               movie1!!
            )
        }
    }
}