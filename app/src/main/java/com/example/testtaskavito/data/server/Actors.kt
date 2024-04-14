package com.example.testtaskavito.data.server

import com.google.gson.annotations.SerializedName

data class Actors (
    @SerializedName("docs")
    var docs: ArrayList<ActorsDocs> = arrayListOf()
)

data class ActorsDocs (
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("enName")
    var enName: String? = null,
    @SerializedName("photo")
    var photo: String? = null,
)