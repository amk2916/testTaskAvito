package com.example.testtaskavito.data

import MoviesRemoteMediator
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.testtaskavito.data.local.LocalPageSource
import com.example.testtaskavito.data.local.LocalPageSource_Factory
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.data.local.MoviesListDao
import com.example.testtaskavito.data.server.ActorPageSource
import com.example.testtaskavito.data.server.Actors
import com.example.testtaskavito.data.server.FilmByNamePageSource
import com.example.testtaskavito.data.server.MoviesService
import com.example.testtaskavito.data.server.ReviewPageSource
import com.example.testtaskavito.data.server.Reviews
import com.example.testtaskavito.domain.Actor
import com.example.testtaskavito.domain.Movie
import com.example.testtaskavito.domain.Poster
import com.example.testtaskavito.domain.Repository
import com.example.testtaskavito.domain.Review
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
        ageRating: Int?
    ): Flow<PagingData<ModelForListLocal>> {
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
            pagingSourceFactory = { LocalPageSource(movieDao, countryName, ageRating, year) }
        ).flow
    }

    override fun getMoviewByName(
        name: String?,
        flagInternet: Boolean
    ): PagingSource<Int, ModelForListLocal> {
        val pageSource = FilmByNamePageSource(
            apiService = moviesService,
            dao = movieDao,
            query = name,
            flagInternet = flagInternet
        )
        return pageSource
    }


    override fun getActorsForID(idMovie: Int): PagingSource<Int, Actor> {
        val pageSource = ActorPageSource(moviesService, idMovie)
        return pageSource
    }

    override fun getReviewForID(idMovie: Int): PagingSource<Int, Review> {
        val pageSource = ReviewPageSource(moviesService, idMovie)
        return pageSource
    }

    override suspend fun getMovieForID(idServer: Int): Movie {
        return moviesService.getFilmForId(idServer).body()?.toMovie() ?: Movie.DEFAULT_MOVIE

    }

    override suspend fun getPostersByID(idServer: Int, countPosters: Int): List<Poster> {
        Log.e("repository", "tut")
        return moviesService.getPostersByFilmId(limit = countPosters, movieId = idServer).body()?.docs?.map { it.toDomain() }
            ?: emptyList<Poster>()
    }
}
