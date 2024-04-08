package com.example.testtaskavito.data

import com.example.testtaskavito.domain.MovieForList
import java.math.BigDecimal
import java.math.RoundingMode

fun Docs.toMovieForList() : MovieForList{
    val newRating = BigDecimal(rating?.kp?.toDouble()?:0.0)
    val finalRating = newRating.setScale(1,RoundingMode.DOWN).toString()
    return MovieForList(
        id= id,
        poster = poster?.url ?: "R.drawable.ic_launcher_foreground",
        name = name ?: "Неизвестное имя",
        year = year ?: 0,
        ageCategory = ageRating ?: 0,
//        shortDescription = shortDescription?:"-",
        //rating = "КП:${rating?.kp ?: "-"}/IMDB:${rating?.imdb ?: "-"}"
        rating =finalRating
    )
}