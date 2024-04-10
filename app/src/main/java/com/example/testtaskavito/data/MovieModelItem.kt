package com.example.testtaskavito.data

import com.example.testtaskavito.domain.Rating
import com.google.gson.annotations.SerializedName


data class MovieModelItem(
    @SerializedName("docs") val docs: MovieItemDocs = MovieItemDocs()
)

data class MovieItemDocs(
    val id: Int? = null,
    val name: String? = "",
    val year: Int? = null,
    val description: String? = null,
    val rating: Rating = Rating(),
    val movieLength: Int? = null,
    val poster: Picture = Picture()
)