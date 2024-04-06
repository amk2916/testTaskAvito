package com.example.testtaskavito.data

import com.example.testtaskavito.domain.MovieForList

fun Docs.toMovieForList() : MovieForList{
    return MovieForList(
        id= id,
        poster = poster?.url ?: "R.drawable.ic_launcher_foreground",
        name = name ?: "Неизвестное имя",
        shortDescription = shortDescription?:"-",
        rating = "КП:${rating?.kp ?: "-"}/IMDB:${rating?.imdb ?: "-"}"
    )
}