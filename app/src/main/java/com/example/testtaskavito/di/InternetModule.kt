package com.example.testtaskavito.di

import android.app.Application
import com.example.testtaskavito.R
import com.example.testtaskavito.data.MoviesService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
class InternetModule {


    @Provides
    fun providesBaseURL() = "https://api.kinopoisk.dev/"

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
                chain.proceed(request)
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