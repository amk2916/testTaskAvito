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

    @Query("SELECT * FROM cache_movie_list_model")
    fun getAllMovies(): PagingSource<Int, ModelForListLocal>

    @Query("DELETE FROM cache_movie_list_model")
    suspend fun clearMovies()

}