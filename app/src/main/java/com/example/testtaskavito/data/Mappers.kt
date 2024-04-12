package com.example.testtaskavito.data

import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.data.server.Docs
import com.example.testtaskavito.data.server.MovieModelItem
import com.example.testtaskavito.domain.Movie
import java.math.BigDecimal
import java.math.RoundingMode


fun Docs.toCachedMovie(page: Int): ModelForListLocal{
    val newRating = BigDecimal(rating?.kp?.toDouble()?:0.0)
    val finalRating = newRating.setScale(1,RoundingMode.DOWN).toFloat()
    rating?.kp = finalRating
    return ModelForListLocal(
        rating = rating,
        id =  id,
        name = name,
        year = year,
        poster = poster?.url,
        page = page,
        ageRating = ageRating?:0,
        country = countries.toString()
    )
}

fun MovieModelItem.toMovie() : Movie{
    return Movie(
        idFilm = id ?: 0,
        nameFilm = name ?:"",
        description = description ?: "",
        rating = rating,
        posters = poster.url ?: "",
        yearFilm = year ?: 0,
        movieLength = movieLength?.toString() ?: "0"
    )
}