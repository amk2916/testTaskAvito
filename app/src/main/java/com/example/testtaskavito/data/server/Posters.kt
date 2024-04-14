package com.example.testtaskavito.data.server

import com.google.gson.annotations.SerializedName

data class Posters(
    @SerializedName("docs")
    var docs: ArrayList<PostersDocs> = arrayListOf()
)

data class PostersDocs(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("url")
    var url: String? = null,
)