package com.example.testtaskavito.data

import androidx.annotation.IntRange
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("v1.4/movie")
    suspend fun getListFilm(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 10) limit: Int = 10,
      //  @Query("countries.name") name: String
    ): Response<MovieModel>


    @GET("v1.4/movie/{id}")
    suspend fun getFilmForId(
        @Path("id") id: Int
    ) : Response<MovieModelItem>
}