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
    private val movieDao: MoviesListDao
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

//                    // В этом случае вы можете реализовать логику для загрузки предыдущих страниц,
//                    // если это необходимо
//                    val firstItem = state.firstItemOrNull()
//                    val prevKey = firstItem?.id ?: return MediatorResult.Success(endOfPaginationReached = true)
//                    val currentPage = state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.first()?.id ?: 1
//                    val prevPage = if (prevKey > 1) currentPage - 1 else null
//                    // В этом месте вы можете использовать prevPage для загрузки предыдущей страницы данных
//                    // ...
//                    return MediatorResult.Success(endOfPaginationReached = prevPage == null)
                }
                LoadType.APPEND -> {
                    try {
                        val nextPage = state.pages.size + 1
                        Log.e("nextPage", nextPage.toString())
                        val pageSize = state.config.pageSize
                        val response = apiService.getListFilm(nextPage, pageSize)

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
//                    val next = state.pages.size
//                    if(state.firstItemOrNull()==null||next < 15  ) {
//                        return MediatorResult.Success(endOfPaginationReached = true)
//                    } else {
//                        state.firstItemOrNull()!!.page + 1
//                    }
//                    val page =  state.closestPageToPosition(state.firstItemOrNull()?.id?:0)
//                    val newPage = page?.nextKey
//                    val prevKey = page?.prevKey
//                    Log.e("newPage", newPage.toString())
//                    Log.e("prevKey", prevKey.toString())
//                    if(newPage==null) {
//
//                        return MediatorResult.Success(endOfPaginationReached = true)
//                    }else{
//                        newPage+1
//                    }
//                    // В этом случае вы можете реализовать логику для загрузки следующих страниц,
//                    // если это необходимо
//                    val lastItem = state.lastItemOrNull()
//                    val page = state.pages.firstOrNull{
//                        it.data.first()
//                    }
//                    val nextPage = lastItem?.id ?: return MediatorResult.Success(endOfPaginationReached = true)
//                    nextPage + 1
                }
            }

            val pageSize = state.config.pageSize
            val response = apiService.getListFilm(page, pageSize)

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