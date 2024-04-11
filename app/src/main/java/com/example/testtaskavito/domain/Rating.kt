package com.example.testtaskavito.domain

data class Rating(
    var kp: Float? = null,
    val imdb: Float? = null,
    val tmdb: Float? = null,
    val filmCritics: Float? = null,
    val russianFilmCritics: Float? = null,
    val await: Float? = null
)