package com.example.testtaskavito.data

import com.example.testtaskavito.domain.Rating
import com.google.gson.annotations.SerializedName


data class MovieModel(

    @SerializedName("docs") var docs: ArrayList<Docs> = arrayListOf(),
    @SerializedName("total") var total: Int? = null,
    @SerializedName("limit") var limit: Int? = null,
    @SerializedName("page") var page: Int? = null,
    @SerializedName("pages") var pages: Int? = null

)


data class Docs(
    @SerializedName("rating") var rating: Rating?,
    @SerializedName("backdrop") var backdrop: Picture?, //скриншоты сериала
    @SerializedName("movieLength") var movieLength: Int?, //длительность
    @SerializedName("id") var id: Int,
    @SerializedName("type") var type: String?, //тип
    @SerializedName("name") var name: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("year") var year: Int?,
    @SerializedName("poster") var poster: Picture? ,
    @SerializedName("genres") var genres: ArrayList<Genres> = arrayListOf(), // жанры
    @SerializedName("countries") var countries: ArrayList<Countries> = arrayListOf(), // страны
    @SerializedName("typeNumber") var typeNumber: Int?, //тип в числовом представлении
    @SerializedName("ratingMpaa") var ratingMpaa: String?, //возрастной рейтинг по MPAA
    @SerializedName("shortDescription") var shortDescription: String? ,
    @SerializedName("ageRating") var ageRating: Int?, //возрастной рейтинг
    @SerializedName("logo") var logo: Logo?,
    @SerializedName("isSeries") var isSeries: Boolean?, // сериал или нет
    @SerializedName("seriesLength") var seriesLength: String?  // продолжительность серии
)

data class Picture(

    @SerializedName("url") var url: String?,
    @SerializedName("previewUrl") var previewUrl: String?

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