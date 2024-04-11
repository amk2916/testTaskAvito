package com.example.testtaskavito.domain

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.testtaskavito.data.local.ModelForListLocal
import kotlinx.coroutines.flow.Flow
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

    fun getMovies(
        countryName: String? = null ,
        year: Int? =null,
        ageRating: Int? = null
    ) : Flow<PagingData<ModelForListLocal>> {
        return repository.getMovies(countryName,year , ageRating)
    }
}