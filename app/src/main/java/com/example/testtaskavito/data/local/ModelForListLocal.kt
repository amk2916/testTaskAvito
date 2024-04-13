package com.example.testtaskavito.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
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
    @TypeConverters(RatingConverter::class)
    var rating: Rating?,
    @PrimaryKey
    var id: Int,
    var name: String?,
    var year: Int?,
    var poster: String?,
    var page:Int,
    var ageRating: Int,
    var country: String
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

