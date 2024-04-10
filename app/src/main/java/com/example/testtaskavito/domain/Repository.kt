package com.example.testtaskavito.domain

import androidx.paging.PagingSource

interface Repository {
    fun getMovies(): PagingSource<Int, MovieForList>
    suspend fun getMovieForID(idServer: Int?/*, idLocal: Long?*/): Movie?
}