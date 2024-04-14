package com.example.testtaskavito.data.server

import androidx.annotation.IntRange
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    //
    @GET("v1.4/movie")
    suspend fun getListFilm(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 50) limit: Int = 30,
        @Query("countries.name") countryName: String? = null,
        @Query("year") year: Int? =  null,
        @Query("ageRating") ageRating: Int? = null
      //  @Query("countries.name") name: String
    ): Response<MovieModel>

    @GET("v1.4/movie/{id}")
    suspend fun getFilmForId(
        @Path("id") id: Int
    ) : Response<MovieModelItem>


    @GET("v1.4/person")
    suspend fun getActorsFilmId(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 50) limit: Int = 30,
        @Query("movies.id") movieId: Int? =  null
    ) : Response<Actors>

    @GET("v1.4/review")
    suspend fun getReviewsFilmId(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 50) limit: Int = 30,
        @Query("&movieId") movieId: Int? =  null
    ) : Response<Reviews>




}