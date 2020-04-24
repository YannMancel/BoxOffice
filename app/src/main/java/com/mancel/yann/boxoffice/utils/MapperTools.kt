package com.mancel.yann.boxoffice.utils

import com.mancel.yann.boxoffice.models.Film
import com.mancel.yann.boxoffice.models.OMDbFilm

/**
 * Created by Yann MANCEL on 02/04/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.utils
 */
object MapperTools {

    /**
     * A mapper to convert [OMDbFilm] to [Film]
     * @param omdbFilm a [OMDbFilm]
     * @return a [Film]
     */
    fun fromOMDbFilmToFilm(omdbFilm: OMDbFilm): Film {
        return Film(
            id = omdbFilm.imdbID,
            title = omdbFilm.title,
            released = omdbFilm.released,
            genre = omdbFilm.genre,
            director = omdbFilm.director,
            actors = omdbFilm.actors,
            synopsis = omdbFilm.plot,
            poster = omdbFilm.poster,
            critics = omdbFilm.metascore,
            audience = omdbFilm.imdbRating
        )
    }
}