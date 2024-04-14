package com.example.testtaskavito.data.server

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testtaskavito.data.Broacast
import com.example.testtaskavito.data.toDomain
import com.example.testtaskavito.domain.Actor
import retrofit2.HttpException

//сделать один общий
class ActorPageSource (
    private val apiService: MoviesService,
    private val movieId: Int
) : PagingSource<Int, Actor>() {
    companion object {
        val TAG = "TEAST"
    }
    override fun getRefreshKey(state: PagingState<Int, Actor>): Int? {
        Log.e(TAG, "getRefreshKey: 1" )
        val anchorPosition = state.anchorPosition ?: return null
        Log.e(TAG, "getRefreshKey: 2" )
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        Log.e(TAG, "getRefreshKey: 3" )
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Actor> {

        val page: Int = params.key ?: 1
        Log.e("TEST", "load: $movieId", )
        val pageSize: Int = params.loadSize.coerceAtMost(10)


        val response = apiService.getActorsFilmId(page, pageSize, movieId)
        response.isSuccessful
        Log.e("TEST", "load: ${response.isSuccessful}", )
        if (response.isSuccessful) {
            val movies = checkNotNull(response.body()).docs.map { it.toDomain() }

            val nextKey = if (movies.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1


            return LoadResult.Page(movies, prevKey, nextKey)
        } else {
            Broacast.pushError(response.message())
            return LoadResult.Error(HttpException(response))
        }

    }
}