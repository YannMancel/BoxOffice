package com.mancel.yann.boxoffice.models

import com.squareup.moshi.JsonClass

/**
 * Created by Yann MANCEL on 02/04/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.models
 */
@JsonClass(generateAdapter = true)
data class Movie(
    var id: String? = null,
    var title: String? = null,
    var released: String? = null,
    var genre: String? = null,
    var director: String? = null,
    var actors: String? = null,
    var synopsis: String? = null,
    var poster: String? = null,
    var critics: String? = null,
    var audience: String? = null
) {

    // NESTED CLASSES ------------------------------------------------------------------------------

    /**
     * A [Comparator] of [Movie] subclass.
     */
    class AZTitleComparator : Comparator<Movie> {
        override fun compare(left: Movie?, right: Movie?): Int {
            // Comparison on the film's title
            val titleLeft = left?.title ?: ""
            val titleRight = right?.title ?: ""

            return titleLeft.compareTo(titleRight, ignoreCase = true)
        }
    }
}