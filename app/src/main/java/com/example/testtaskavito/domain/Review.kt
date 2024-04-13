package com.example.testtaskavito.domain

import com.google.gson.annotations.SerializedName

data class Review(
    var id: Int? = null,
    var review: String? = null,
    var author: String? = null,
)