package com.mancel.yann.boxoffice.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.models
 */
@JsonClass(generateAdapter = true)
data class OMDbMovie (
    @Json(name = "Title")
    var title: String? = null,
    @Json(name = "Year")
    var year: String? = null,
    @Json(name = "Rated")
    var rated: String? = null,
    @Json(name = "Released")
    var released: String? = null,
    @Json(name = "Runtime")
    var runtime: String? = null,
    @Json(name = "Genre")
    var genre: String? = null,
    @Json(name = "Director")
    var director: String? = null,
    @Json(name = "Writer")
    var writer: String? = null,
    @Json(name = "Actors")
    var actors: String? = null,
    @Json(name = "Plot")
    var plot: String? = null,
    @Json(name = "Language")
    var language: String? = null,
    @Json(name = "Country")
    var country: String? = null,
    @Json(name = "Awards")
    var awards: String? = null,
    @Json(name = "Poster")
    var poster: String? = null,
    @Json(name = "Ratings")
    var ratings: MutableList<OMDbRating>? = null,
    @Json(name = "Metascore")
    var metascore: String? = null,
    @Json(name = "imdbRating")
    var imdbRating: String? = null,
    @Json(name = "imdbVotes")
    var imdbVotes: String? = null,
    @Json(name = "imdbID")
    var imdbID: String? = null,
    @Json(name = "Type")
    var type: String? = null,
    @Json(name = "DVD")
    var dVD: String? = null,
    @Json(name = "BoxOffice")
    var boxOffice: String? = null,
    @Json(name = "Production")
    var production: String? = null,
    @Json(name = "Website")
    var website: String? = null,
    @Json(name = "Response")
    var response: String? = null
)