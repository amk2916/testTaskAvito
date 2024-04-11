package com.example.testtaskavito.data

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
    //private val moviesPageSource: MoviesPageSource,
    private val moviesService: MoviesService,
    private val moviesRemoteMediator: MoviesRemoteMediator,
    private val movieDao: MoviesListDao
) : Repository {

    override fun getMovies(): Flow<PagingData<ModelForListLocal>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = moviesRemoteMediator,
            pagingSourceFactory = { movieDao.getAllMovies() }
        ).flow
    }
//    override fun getMovies(): PagingSource<Int, MovieForList> {
//        return moviesPageSource
//    }

    override suspend fun getMovieForID(idServer: Int?): Movie? {
        return moviesService.getFilmForId(idServer!!).body()?.toMovie()
    }
}