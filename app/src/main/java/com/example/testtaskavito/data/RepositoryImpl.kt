package com.example.testtaskavito.data

import MoviesRemoteMediator
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testtaskavito.data.local.LocalPageSource
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.data.local.MoviesListDao
import com.example.testtaskavito.data.server.MoviesService
import com.example.testtaskavito.domain.Movie
import com.example.testtaskavito.domain.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val movieDao: MoviesListDao
) : Repository {


    override fun getMovies(
        countryName: String?,
        year: Int?,
        ageRating: Int?): Flow<PagingData<ModelForListLocal>> {
        // Создаем MoviesRemoteMediator
        val remoteMediator = MoviesRemoteMediator(
            apiService = moviesService,
            movieDao = movieDao,
            nameCountry = countryName,
            year = year,
            ageRating = ageRating
        )

        // Создаем Pager для загрузки данных с помощью RemoteMediator и LocalPageSource
        return Pager(
            config = PagingConfig(pageSize = 16),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { LocalPageSource(movieDao) }
        ).flow
    }

    override suspend fun getMovieForID(idServer: Int?): Movie? {
        return moviesService.getFilmForId(idServer!!).body()?.toMovie()
    }
}