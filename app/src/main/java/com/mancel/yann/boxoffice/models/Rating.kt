package com.mancel.yann.boxoffice.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.models
 */
@JsonClass(generateAdapter = true)
data class Rating (
    @Json(name = "Source")
    var source : String? = null,
    @Json(name = "Value")
    var value : String? = null
)