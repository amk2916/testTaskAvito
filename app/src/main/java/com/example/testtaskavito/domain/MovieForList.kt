package com.example.testtaskavito.domain

data class MovieForList(
    val id: Int,
    val poster: String,
    val name: String,
    val year: Int,
    val ageCategory: Int,
    val rating: String,
    var idLocal: Long? = null
)