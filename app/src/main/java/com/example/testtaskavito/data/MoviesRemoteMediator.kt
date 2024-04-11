package com.example.testtaskavito.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.data.local.MoviesListDao
import retrofit2.HttpException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator @Inject constructor(
    private val apiService: MoviesService,
    private val movieDao: MoviesListDao,

    private val nameCountry: String? = null,
    private val year: Int? = null,
    private val ageRating: Int? = null

) : RemoteMediator<Int, ModelForListLocal>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ModelForListLocal>
    ): MediatorResult {
        //try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    try {
                        val Page = state.lastItemOrNull()?.page
                            ?: 0


                        val nextPage = Page +1
                        Log.e("nextPage", nextPage.toString())
                        val pageSize = state.config.pageSize
                        val response = apiService.getListFilm(nextPage, pageSize, nameCountry, year, ageRating)

                        if (response.isSuccessful) {
                            val movies = response.body()?.docs?.map { it.toCachedMovie(nextPage) } ?: emptyList()
                            movieDao.insertAll(movies)
                            val endOfPaginationReached = movies.isEmpty()

                            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                        } else {
                            Broacast.pushError(response.message())
                            return MediatorResult.Error(HttpException(response))
                        }
                    } catch (exception: Exception) {
                        Broacast.pushError(exception.toString())
                        return MediatorResult.Error(exception)
                    }
                }
            }

            val pageSize = state.config.pageSize
            val response = apiService.getListFilm(page, pageSize,  nameCountry, year, ageRating)

            if (response.isSuccessful) {
                val movies = response.body()?.docs?.map { it.toCachedMovie(page) } ?: emptyList()
                movieDao.insertAll(movies)
                val endOfPaginationReached = movies.isEmpty()

                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                Broacast.pushError(response.message())
                return MediatorResult.Error(HttpException(response))
            }
//        } catch (exception: Exception) {
//            Broacast.pushError(exception.toString())
//            return MediatorResult.Error(exception)
//        }
    }

}