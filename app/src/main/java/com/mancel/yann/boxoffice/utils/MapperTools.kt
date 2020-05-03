package com.mancel.yann.boxoffice.utils

import com.mancel.yann.boxoffice.models.Movie
import com.mancel.yann.boxoffice.models.OMDbMovie

/**
 * Created by Yann MANCEL on 02/04/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.utils
 */
object MapperTools {

    /**
     * A mapper to convert [OMDbMovie] to [Movie]
     * @param omdbMovie a [OMDbMovie]
     * @return a [Movie]
     */
    fun fromOMDbMovieToMovie(omdbMovie: OMDbMovie): Movie {
        return Movie(
            id = omdbMovie.imdbID,
            title = omdbMovie.title,
            released = omdbMovie.released,
            genre = omdbMovie.genre,
            director = omdbMovie.director,
            actors = omdbMovie.actors,
            synopsis = omdbMovie.plot,
            poster = omdbMovie.poster,
            critics = omdbMovie.metascore,
            audience = omdbMovie.imdbRating
        )
    }
}