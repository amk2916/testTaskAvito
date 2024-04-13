package com.example.testtaskavito.data.server

import com.google.gson.annotations.SerializedName

//коментарии на экране с детализацией
data class Review(
    @SerializedName("docs")
    var docs: ArrayList<ActorsDocs> = arrayListOf(),
)


data class ActorsDocs(
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