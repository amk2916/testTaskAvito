package com.example.testtaskavito.data

import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.data.server.ActorsDocs
import com.example.testtaskavito.data.server.Docs
import com.example.testtaskavito.data.server.MovieModelItem
import com.example.testtaskavito.data.server.ReviewDocs
import com.example.testtaskavito.domain.Actor
import com.example.testtaskavito.domain.Movie
import com.example.testtaskavito.domain.Review
import java.math.BigDecimal
import java.math.RoundingMode


fun ActorsDocs.toDomain(): Actor{
    return Actor(
        id = id,
        name = name?:enName?: "неизвестный",
        photo = photo
    )
}
fun ReviewDocs.toDomain(): Review {
    return Review(
        id = id,
        review = review,
        author = author?: "неизвестный",
    )
}

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
        nameFilm = name ?: "Нет наименования",
        description = description ?: "Нет описания",
        ratingKp = "КП: ${parseRating(rating.kp)}",
        ratingIMDB = "IMDB: ${parseRating(rating.imdb)}",
        ratingTMDB = "TMDB: ${parseRating(rating.tmdb)}",
        posters = poster.url ?: "",
        yearFilm = year ?: 0,
        movieLength = "${movieLength?.toString() ?: "0"} минут"
    )
}

fun parseRating(rating: Float?):String{
    if (rating==null) return "--"
    val newRating = BigDecimal(rating.toDouble())
    return newRating.setScale(1,RoundingMode.DOWN).toString()
}