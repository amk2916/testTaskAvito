package com.example.testtaskavito.domain

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.testtaskavito.data.local.ModelForListLocal
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getMovies(
        countryName: String?,
        year: Int? ,
        ageRating: Int?
    ): Flow<PagingData<ModelForListLocal>>

    fun getActorsForID(idMovie: Int): PagingSource<Int, Actor>
    fun getReviewForID(idMovie: Int):  PagingSource<Int, Review>


    suspend fun getMovieForID(idServer: Int): Movie?
}