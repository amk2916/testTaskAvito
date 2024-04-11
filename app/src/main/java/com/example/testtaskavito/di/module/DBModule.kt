package com.example.testtaskavito.di.module

import android.app.Application
import androidx.room.Room
import com.example.testtaskavito.data.local.AppDataBase
import com.example.testtaskavito.data.local.MoviesListDao
import com.example.testtaskavito.di.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DBModule {
    @Provides
    @ApplicationScope
    fun db(application: Application): AppDataBase = Room
        .databaseBuilder(application, AppDataBase::class.java, "MoviesDataBase")
        .build()

    @Provides
    fun infoModelDao(db: AppDataBase): MoviesListDao = db.getMoviesListDao()
}