package com.example.testtaskavito

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {



    val token = "RKEKDA3-6HHMGHS-K8FP2WN-G5TS661"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor{chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("X-API-KEY", token) // Попробуем передать токен с помощью ретрофита в гет запрос
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(interceptor)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.kinopoisk.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


        val a = retrofit.create(filmApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            a.getListFilm(1, 1, "Россия").enqueue(object : Callback<internetModel> {
                override fun onResponse(p0: Call<internetModel>, p1: Response<internetModel>) {
                    val data = p1.body()
                    Log.e("Response", data.toString())
                }

                override fun onFailure(p0: Call<internetModel>, p1: Throwable) {
                    Log.e("Failure", p1.message.toString())
                }

            })


        }
    }
}

interface filmApi {
    @GET("v1.4/movie")
    fun getListFilm(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("countries.name") name: String
    ): Call<internetModel>

}


data class internetModel(

    @SerializedName("docs") var docs: ArrayList<Docs> = arrayListOf(),
    @SerializedName("total") var total: Int? = null,
    @SerializedName("limit") var limit: Int? = null,
    @SerializedName("page") var page: Int? = null,
    @SerializedName("pages") var pages: Int? = null

)


data class Docs(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("name") var name: String? = null
)
