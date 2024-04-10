package com.example.testtaskavito.domain

import androidx.paging.PagingSource
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: Repository
) {

    /**
     * если есть доступ к сети запрос уходит в сеть,
     * иначе, поиск локально
     */
    fun getMovie(localFlag: Boolean = false): List<Movie>{
        TODO()
    }

    /**
     * то же самое
     */
    fun getMovieForName(name: String, localFlag: Boolean = false) : Movie{
        TODO()
    }

    suspend fun getMovieForID(idServer: Int?/*, idLocal: Long?*/): Movie?{
        return repository.getMovieForID(idServer)
    }

    fun getMovies() : PagingSource<Int, MovieForList>{
        return repository.getMovies()
    }
}