package com.example.testtaskavito.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.testtaskavito.data.Picture
import com.example.testtaskavito.domain.Rating
import com.google.gson.Gson

@Entity(
    tableName = "cache_movie_list_model",
    indices = [
        Index(
            value = ["id"],
            unique = true
        )
    ]

)
data class ModelForListLocal(
    @PrimaryKey(autoGenerate = true)
    var idLocal: Int = 0,
    @TypeConverters(RatingConverter::class)
    var rating: Rating?,
    var id: Int,
    var name: String?,
    var year: Int?,
    var poster: String?,
    var page:Int
)

class RatingConverter {
    @TypeConverter
    fun fromString(value: String?): Rating? {
        return if (value == null) null else Gson().fromJson(value, Rating::class.java)
    }

    @TypeConverter
    fun toString(rating: Rating?): String? {
        return rating?.let { Gson().toJson(it) }
    }
}

