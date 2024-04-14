package com.example.testtaskavito.domain


/**
 *  idFilm - id с сервера
 *  nameFilm - имя фильма
 *  description - описание
 *  rating - рейтинги
 *  posters - ссылка на постер
 *  yearFilm - год выпуска
 *  countryFilm - страна выпуска
 *  typeFilms - movie 1 | tv-series 2 | cartoon  3| anime 4 | animated-series 5 | tv-show 6 todo: подумать
 *  countSeasons - количество сезонов (если это сериал)
 */
data class Movie(
    val idFilm: Int,
    val nameFilm: String,
    val description: String,
    val ratingKp: String,
    val ratingIMDB: String,
    val ratingTMDB: String,
    val posters: String,
    val yearFilm: Int,
   // val countryFilm: Int,
    //val typeFilms: Int,
    //val countSeasons: Int?,
    val movieLength: String
)