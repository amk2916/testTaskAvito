package com.example.testtaskavito.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.log
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.data.local.MoviesListDao
import retrofit2.HttpException
import javax.inject.Inject
@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
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
        try {
            val currentPage: Int = when (loadType) {
                LoadType.REFRESH -> {
                    Log.e("MediatorResult:LoadType.REFRESH", "LoadType.REFRESH")
                    1
                }
                LoadType.PREPEND -> {
                    Log.e("MediatorResult:LoadType.PREPEND", "LoadType.PREPEND")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    Log.e("MediatorResult LoadType.APPEND: lastItem", lastItem.toString())
                    Log.e("MediatorResult LoadType.APPEND: firstItem", state.firstItemOrNull().toString())
                    Log.e("MediatorResult LoadType.APPEND: firstItem", state.pages.size.toString())
                    Log.e("MediatorResult LoadType.APPEND: DB", movieDao.getMovies().toString())
                    Log.e("MediatorResult LoadType.APPEND: pageEmpty", state.isEmpty().toString())

                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    state.pages.size + 1
                }
            }
            Log.e("MediatorResult", "loading...")
            val pageSize = state.config.pageSize
            val response = apiService.getListFilm(currentPage, pageSize, nameCountry, year, ageRating)

            if (response.isSuccessful) {
                val movies = response.body()?.docs?.map { it.toCachedMovie(currentPage) } ?: emptyList()

                if (movies.isEmpty()) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                Log.e("MediatorResult", "loading in db...")
                movieDao.insertAll(movies)

                Log.e("MediatorResult", movies.isEmpty().toString())
                return MediatorResult.Success(endOfPaginationReached = movies.isEmpty())
            } else {
                // Если произошла ошибка, сообщаем об этом через Broacast
                Broacast.pushError(response.message())
                return MediatorResult.Error(HttpException(response))
            }
        } catch (exception: Exception) {
            // Обработка исключений: сообщаем об ошибке через Broacast и возвращаем MediatorResult.Error
            Broacast.pushError(exception.toString())
            return MediatorResult.Error(exception)
        }
    }
}