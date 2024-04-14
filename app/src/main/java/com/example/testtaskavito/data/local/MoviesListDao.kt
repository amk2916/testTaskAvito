package com.example.testtaskavito.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<ModelForListLocal>)

    // TODO: Вызвать для очистки кэша (можно по времени, можно по объему)
    @Query("DELETE FROM cache_movie_list_model")
    suspend fun clearMovies()

    /**
     * Метод для получения фильмов из локальной базы данных в зависимости от номера страницы и размера загрузки.
     *
     * @param offset зависит от номера страниц и размера.
     * @param pageSize Размер загрузки (количество записей, которое нужно загрузить).
     * @return Список фильмов для текущей страницы.
     */
    @Query(
        """
        SELECT * FROM cache_movie_list_model
        WHERE (:year IS NULL OR year = :year)
          AND (:ageRating IS NULL OR ageRating = :ageRating)
          AND (:country IS NULL OR UPPER(country) LIKE UPPER('%' || :country || '%'))
          AND (:name IS NULL OR UPPER(name) LIKE UPPER('%' || :name || '%'))
        ORDER BY id
        LIMIT :pageSize OFFSET :offset
    """
    )
    suspend fun getMoviesByPage(
        offset: Int,
        pageSize: Int,
        year: Int? = null,
        ageRating: Int? = null,
        country: String? = null,
        name: String? = null
    ): List<ModelForListLocal>

}