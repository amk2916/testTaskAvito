package com.example.testtaskavito.data.server

import com.google.gson.annotations.SerializedName

//коментарии на экране с детализацией
data class Reviews(
    @SerializedName("docs")
    var docs: ArrayList<ReviewDocs> = arrayListOf(),
)


data class ReviewDocs(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("movieId")
    var movieId: Int? = null,
    @SerializedName("review")
    var review: String? = null,
    @SerializedName("date") var
    date: String? = null,
    @SerializedName("author")
    var author: String? = null,
)