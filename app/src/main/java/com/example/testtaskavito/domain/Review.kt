package com.example.testtaskavito.domain

//коментарии на экране с детализацией
data class Review(
    val idFilm: Long,
    val author: String,
    val texReview: String,
    val typeReview: TypeReview,
)

enum class TypeReview{
    Positive{
        override fun toString(): String = "Положительный"
    },

    Negative{
        override fun toString(): String = "Отрицательный"
    }
}