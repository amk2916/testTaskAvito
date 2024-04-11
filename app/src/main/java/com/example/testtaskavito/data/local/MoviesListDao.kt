package com.example.testtaskavito.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<ModelForListLocal>)

//    @Query("SELECT * FROM cache_movie_list_model")
//    fun getAllMovies(): PagingSource<Int, ModelForListLocal>

    @Query("DELETE FROM cache_movie_list_model")
    suspend fun clearMovies()


    @Query("""
    SELECT * FROM cache_movie_list_model
    WHERE (:year IS NULL OR year = :year)
    AND (:ageRating IS NULL OR ageRating = :ageRating)
    AND (:country IS NULL OR UPPER(country) LIKE UPPER('%' || :country || '%'))
    """)
    fun getAllMovies(country: String?= null, ageRating: Int? = null, year: Int? =null): PagingSource<Int, ModelForListLocal>

}