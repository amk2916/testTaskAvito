package com.example.testtaskavito.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.data.local.MoviesListDao
import com.example.testtaskavito.domain.Movie
import com.example.testtaskavito.domain.MovieForList
import com.example.testtaskavito.domain.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val movieDao: MoviesListDao
) : Repository {

    private fun createMoviesRemoteMediator(
        countryName: String? = null,
        year: Int? = null,
        ageRating: Int? = null
    ): MoviesRemoteMediator {
        return MoviesRemoteMediator(
            apiService = moviesService,
            movieDao = movieDao,
            nameCountry  = countryName,
            year = year,
            ageRating = ageRating
        )
    }

    override fun getMovies(
        countryName: String?,
        year: Int?,
        ageRating: Int?
    ): Flow<PagingData<ModelForListLocal>> {
        Log.e("Repository", "getMovies")
        val moviesRemoteMediator = createMoviesRemoteMediator(countryName, year, ageRating)
        return Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = moviesRemoteMediator) {
                movieDao.getAllMovies(countryName, year, ageRating)
            }.flow

    }

    override suspend fun getMovieForID(idServer: Int?): Movie? {
        return moviesService.getFilmForId(idServer!!).body()?.toMovie()
    }
}