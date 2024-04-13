package com.example.testtaskavito.domain

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.data.server.Actors
import com.example.testtaskavito.data.server.Review
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getMovies(
        countryName: String?,
        year: Int? ,
        ageRating: Int?
    ): Flow<PagingData<ModelForListLocal>>

    fun getActorsForID(idMovie: Int): Flow<PagingData<Actors>>
    fun getReviewForID(idMovie: Int): Flow<PagingData<Review>>


    suspend fun getMovieForID(idServer: Int): Movie?
}