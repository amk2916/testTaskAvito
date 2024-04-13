import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.data.local.MoviesListDao
import com.example.testtaskavito.data.server.MoviesService
import com.example.testtaskavito.data.toCachedMovie
import retrofit2.HttpException

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
            // Определяем текущую страницу
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    Log.e("RemoteMediator01","Refresh")
                    1
                }
                LoadType.APPEND -> {
                    Log.e("RemoteMediator1",state.lastItemOrNull().toString())
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                    Log.e("RemoteMediator2", state.pages.size.toString())
                    Log.e("RemoteMediator3", state.toString())

                    state.pages.size + 1
                }
                LoadType.PREPEND -> {
                    Log.e("RemoteMediator02","PREPEND")
                    // Предотвращаем загрузку предыдущих страниц, так как обычно это не требуется
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            // Задаем размер страницы
            val pageSize = state.config.pageSize+6
            Log.e("RemoteMediator4","response")
            // Загружаем фильмы с удаленного сервера
            val response = apiService.getListFilm(
                currentPage,
                pageSize,
                nameCountry,
                year, ageRating)

            // Проверяем успешность запроса
            if (!response.isSuccessful) {
                return MediatorResult.Error(HttpException(response))
            }

            // Получаем список фильмов из ответа
            val movies = response.body()?.docs?.map { it.toCachedMovie(currentPage) } ?: emptyList()

            // Если список фильмов пустой, возвращаем успех с флагом, что достигнут конец пагинации.
            if (movies.isEmpty()) {
                Log.e("RemoteMediator5", "1")
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            // Сохраняем фильмы в локальной базе данных
            movieDao.insertAll(movies)

            Log.e("RemoteMediator6", (movies.size < pageSize).toString())
            // Возвращаем успех с флагом, что достигнут конец пагинации, если количество фильмов меньше, чем размер страницы
            return MediatorResult.Success(endOfPaginationReached = movies.size < pageSize)
        } catch (exception: Exception) {
            // Если произошла ошибка, возвращаем MediatorResult.Error
            return MediatorResult.Error(exception)
        }
    }
}