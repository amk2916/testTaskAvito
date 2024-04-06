package com.example.testtaskavito.domain

class GetMoviesUseCase {

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

    fun getMovieForID(idServer: Long?, idLocal: Long?): Movie{
        TODO()
    }
}