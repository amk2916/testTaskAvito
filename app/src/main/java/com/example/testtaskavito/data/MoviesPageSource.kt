package com.example.testtaskavito.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testtaskavito.domain.MovieForList
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import javax.inject.Inject

object Broacast {

    val errorUpdates = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    fun pushError(string: String) {
        errorUpdates.tryEmit(string)
    }
}

class MoviesPageSource @Inject constructor(
    private val apiService: MoviesService
    // broadcast
) : PagingSource<Int, MovieForList>() {
    override fun getRefreshKey(state: PagingState<Int, MovieForList>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieForList> {

        val page: Int = params.key ?: 1

        val pageSize: Int = params.loadSize.coerceAtMost(15)

        val response = apiService.getListFilm(page, pageSize)
        response.isSuccessful

            if (response.isSuccessful) {
                Log.e("ErrorHTTP", response.body().toString())
                val movies = checkNotNull(response.body()).docs.map { it.toMovieForList() }

                val nextKey = if (movies.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1


                return LoadResult.Page(movies, prevKey, nextKey)
            } else {
                Log.e("ErrorHTTP", response.message())
                Broacast.pushError(response.message())
                return LoadResult.Error(HttpException(response))
            }

    }
}