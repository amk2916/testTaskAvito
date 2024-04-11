package com.example.testtaskavito.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        ModelForListLocal::class
    ],
    version = 1,
    exportSchema = false,

)
@TypeConverters(RatingConverter::class)
abstract class AppDataBase : RoomDatabase(){
    abstract fun getMoviesListDao():MoviesListDao
}