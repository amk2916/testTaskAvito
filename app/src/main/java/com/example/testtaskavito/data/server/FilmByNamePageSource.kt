package com.example.testtaskavito.data.server

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testtaskavito.data.Broacast
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.data.local.MoviesListDao
import com.example.testtaskavito.data.toCachedMovie
import com.example.testtaskavito.data.toDomain
import com.example.testtaskavito.domain.Actor
import retrofit2.HttpException

// TODO: по хорошему, надо кэшироваться и соединятся с общей выдачей, тогда
// не нужны фдаги о доступе в интерне. Не успел переделать
class FilmByNamePageSource(
    private val apiService: MoviesService,
    private val dao: MoviesListDao,
    private val query: String?,
    private val flagInternet: Boolean
) : PagingSource<Int, ModelForListLocal>() {
    override fun getRefreshKey(state: PagingState<Int, ModelForListLocal>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ModelForListLocal> {

        if (flagInternet) {

            val page: Int = params.key ?: 1
            val pageSize: Int = params.loadSize.coerceAtMost(10)


            val response = apiService.getFilmByName(page, pageSize, query)

            if (response.isSuccessful) {
                val movies = checkNotNull(response.body()).docs.map { it.toCachedMovie(page) }

                val nextKey = if (movies.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1


                return LoadResult.Page(movies, prevKey, nextKey)
            } else {
                Broacast.pushError(response.message())
                return LoadResult.Error(HttpException(response))
            }
        } else {
            try {
                val page = params.key ?: 1
                val pageSize = params.loadSize
                val offset = pageSize * (page - 1)
                val movies = dao.getMoviesByPage(
                    offset,
                    pageSize,
                    null,
                    null,
                    null,
                    query
                )
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
                return LoadResult.Error(ex)
            }
        }

    }
}