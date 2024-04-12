package com.example.testtaskavito.data.server

import com.example.testtaskavito.data.server.Docs
import com.google.gson.annotations.SerializedName


data class MovieModel(

    @SerializedName("docs") var docs: ArrayList<Docs> = arrayListOf(),
    @SerializedName("total") var total: Int? = null,
    @SerializedName("limit") var limit: Int? = null,
    @SerializedName("page") var page: Int? = null,
    @SerializedName("pages") var pages: Int? = null

)


data class Picture(

    @SerializedName("url") var url: String? = "",
    @SerializedName("previewUrl") var previewUrl: String? =""

)

data class Genres(

    @SerializedName("name") var name: String?

)

data class Logo(

    @SerializedName("url") var url: String?

)


data class Countries(

    @SerializedName("name") var name: String?

)