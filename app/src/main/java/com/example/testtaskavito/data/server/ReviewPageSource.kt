package com.example.testtaskavito.data.server

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testtaskavito.data.Broacast
import com.example.testtaskavito.data.toDomain
import com.example.testtaskavito.domain.Review
import retrofit2.HttpException
import javax.inject.Inject

//TODO сделать один общий pageSource
class ReviewPageSource @Inject constructor(
    private val apiService: MoviesService,
    private val movieId: Int
) : PagingSource<Int, Review>() {
    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {

        val page: Int = params.key ?: 1

        val pageSize: Int = params.loadSize.coerceAtMost(10)

        val response = apiService.getReviewsFilmId(page, pageSize, movieId)
        response.isSuccessful

        if (response.isSuccessful) {
            val review = checkNotNull(response.body()).docs.map { it.toDomain() }

            val nextKey = if (review.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1


            return LoadResult.Page(review, prevKey, nextKey)
        } else {
            Broacast.pushError(response.message())
            return LoadResult.Error(HttpException(response))
        }

    }
}