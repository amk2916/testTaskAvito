package com.example.testtaskavito.domain

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.testtaskavito.data.local.ModelForListLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend fun getMovieForID(idServer: Int): Movie?{
        return repository.getMovieForID(idServer)
    }

    fun getMovies(
        countryName: String? = null ,
        year: Int? =null,
        ageRating: Int? = null
    ) : Flow<PagingData<ModelForListLocal>> {
        return repository.getMovies(countryName,year , ageRating)
    }

     fun getMovieByName(
         name: String? = null,
         flagInternet: Boolean
     ) :  PagingSource<Int, ModelForListLocal>{
         return repository.getMoviewByName(name, flagInternet)
     }
}