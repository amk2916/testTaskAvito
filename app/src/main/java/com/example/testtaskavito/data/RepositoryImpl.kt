package com.example.testtaskavito.data

import androidx.paging.PagingSource
import com.example.testtaskavito.domain.MovieForList
import com.example.testtaskavito.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val moviesPageSource: MoviesPageSource
) : Repository {
    override fun getMovies(): PagingSource<Int, MovieForList> {
        return moviesPageSource
    }
}