package com.example.testtaskavito.domain


/**
 *  idLocal - для работы в офлайн режиме
 *  idFilm - id с сервера
 *  nameFilm - имя фильма
 *  description - описание
 *  rating - рейтинги
 *  reviews - комментарии
 *  posters - ссылка на постер
 *  yearFilm - год выпуска
 *  countryFilm - страна выпуска
 *  typeFilms - movie 1 | tv-series 2 | cartoon  3| anime 4 | animated-series 5 | tv-show 6 todo: подумать
 *  actors - Список актеров фильма
 *  countSeasons - количество сезонов (если это сериал)
 */
data class Movie(
    val idLocal: Long?,
    val idFilm: Long,
    val nameFilm: String,
    val description: String,
    val rating: Rating,
    val reviews: List<Review>,
    val posters: String,
    val yearFilm: Int,
    val countryFilm: Int,
    val typeFilms: Int,
    val actors: List<String>,
    val countSeasons: Int?,
)