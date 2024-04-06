package com.example.testtaskavito.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testtaskavito.domain.MovieForList
import retrofit2.HttpException

class MoviesPageSource(
    private val apiService: MoviesService
) : PagingSource<Int, MovieForList>() {
    override fun getRefreshKey(state: PagingState<Int, MovieForList>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieForList> {
        //пустой запрос

        val page: Int = params.key ?: 1

        val pageSize: Int = params.loadSize.coerceAtMost(50)

        val response = apiService.getListFilm(page, pageSize)

        if (response.isSuccessful) {
            val movies = checkNotNull(response.body()).docs.map { it.toMovieForList() }

            val nextKey = if (movies.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1


            return LoadResult.Page(movies, prevKey, nextKey)
        } else {
            return LoadResult.Error(HttpException(response))
        }

    }
}