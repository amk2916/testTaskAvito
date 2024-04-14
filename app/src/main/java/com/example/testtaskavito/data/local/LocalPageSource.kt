package com.example.testtaskavito.data.local

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import javax.inject.Inject

class LocalPageSource @Inject constructor(
    private var dao: MoviesListDao,
    private var countryName: String?,
    private val ratingAge: Int?,
    private val year: Int?
) : PagingSource<Int, ModelForListLocal>() {
    override fun getRefreshKey(state: PagingState<Int, ModelForListLocal>): Int? {
        Log.e(" PagingSource getRefreshKey", state.toString())
        val anchorPosition = state.anchorPosition ?: return null

        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null

        // Возвращаем ключ обновления: предыдущий ключ плюс один или следующий ключ минус один.
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ModelForListLocal> {
        // Получаем текущий номер страницы
        val page = params.key ?: 1
        val pageSize = params.loadSize
        val offset = pageSize * (page - 1)
        try {
            val counter = 10
            var movies: List<ModelForListLocal> = emptyList()
            var i = 0
            // TODO: Придумал временное решение для того чтобы избавится от бага (вставка в MovieRemoteMediator)
            // проходит дольше чем вызов select, поэтому приходится ждать пока закэшируется
            // по идее можно делать флоу на событие окончания вставки и подписаться на него
            // данное решение очень замедляет работу
            movies = dao.getMoviesByPage(offset, pageSize, year, ratingAge, countryName)
            if (movies.isEmpty()) {
                while (movies.isEmpty() || i < counter) {
                    movies = dao.getMoviesByPage(offset, pageSize)
                    i++
                    delay(1000)
                }
            }
            Log.e("PagingSource", offset.toString() +"/"+page)

            // Если список фильмов пустой, возвращаем успех с флагом, что достигнут конец пагинации.
            if (movies.isEmpty()) {
                Log.e("PagingSource", "tut")
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

            // Определяем предыдущий и следующий ключи для пагинации.
            val prevKey = if (page == 1) null else page - 1
            val nextKey = page + 1
            Log.e("RemoteMediator: prev next", prevKey.toString() + " " + nextKey.toString())
            // Возвращаем страницу с данными
            return LoadResult.Page(
                data = movies,
                prevKey = prevKey,
                nextKey = nextKey
            )


        } catch (ex: Exception) {
            // Если произошла ошибка во время загрузки, возвращаем ошибку.
            return LoadResult.Error(ex)
        }
    }

}