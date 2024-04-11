package com.example.testtaskavito.di.module

import android.app.Application
import com.example.testtaskavito.R
import com.example.testtaskavito.data.MoviesService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

@Module
class InternetModule {

    @Provides
    fun providesOkHttp(application: Application): OkHttpClient {
        val token = application.resources.getString(R.string.api_key)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient()
            .newBuilder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header(
                        "X-API-KEY",
                        token
                    ) // Попробуем передать токен с помощью ретрофита в гет запрос
                val request = requestBuilder.build()
                try {
                    chain.proceed(request)
                } catch (e: Exception) {
                    val body = with(ResponseBody) {
                        "Error body ".toResponseBody()
                    }
                    okhttp3.Response.Builder()
                        .code(500)
                        .protocol(Protocol.HTTP_1_0)
                        .request(request)
                        .body(body)
                        .message(e.message ?: "Def error")
                        .build()
                }
            }
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.kinopoisk.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesMoviesService(retrofit: Retrofit): MoviesService = retrofit.create()

}