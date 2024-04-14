package com.example.testtaskavito.domain

data class Poster (
    val id: String,
    val poster: String
){
    companion object{
        val DEFAULT_POSTER = Poster("", "")
    }
}