package com.example.testtaskavito.data.server

import com.example.testtaskavito.domain.Rating

//Модель для детализации фильма
data class MovieModelItem(
    val id: Int? = null,
    val name: String? = "",
    val year: Int? = null,
    val description: String? = null,
    val rating: Rating = Rating(),
    val movieLength: Int? = null,
    val poster: Picture = Picture()
)