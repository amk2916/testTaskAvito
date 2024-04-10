package com.example.testtaskavito.data

import androidx.paging.PagingSource
import com.example.testtaskavito.domain.Movie
import com.example.testtaskavito.domain.MovieForList
import com.example.testtaskavito.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val moviesPageSource: MoviesPageSource,
    private val moviesService: MoviesService
) : Repository {
    override fun getMovies(): PagingSource<Int, MovieForList> {
        return moviesPageSource
    }

    override suspend fun getMovieForID(idServer: Int?): Movie? {
        return moviesService.getFilmForId(idServer!!).body()?.toMovie()
    }
}