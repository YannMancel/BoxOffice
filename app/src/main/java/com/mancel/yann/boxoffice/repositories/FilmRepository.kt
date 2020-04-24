package com.mancel.yann.boxoffice.repositories

import android.content.Context
import com.mancel.yann.boxoffice.models.Film
import io.reactivex.Single

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.repositories
 */
interface FilmRepository {

    // METHODS -------------------------------------------------------------------------------------

    // -- Stream --

    /**
     * Gets a stream to fetch the [Film] by its title
     * @param title         a [String] that contains the title
     * @param key           a [String] that contains your application's API key
     * @param resultType    a [String] that defines the type of result to return
     * @param dataType      a [String] that defines the data type to return
     * @param plot          a [String] that is either short or full plot
     * @return a [Single] of [Film]
     */
    fun getStreamToFetchFilmByTitle(
        title: String,
        key: String,
        resultType: String = "movie",
        dataType: String = "json",
        plot: String = "short"
    ): Single<Film>

    /**
     * Gets a stream to fetch the [List] of [Film]
     * @param key           a [String] that contains your application's API key
     * @param resultType    a [String] that defines the type of result to return
     * @param dataType      a [String] that defines the data type to return
     * @param plot          a [String] that is either short or full plot
     * @return a [Single] of [List] of [Film]
     */
    fun getStreamToFetchFilms(
        key: String,
        resultType: String = "movie",
        dataType: String = "json",
        plot: String = "short"
    ): Single<List<Film>>

    // -- Data --

    /**
     * Saves the rating of [Film]
     * @param context   a [Context]
     * @param film      a [Film]
     * @param rating    a [Float] that contains the rating value
     */
    fun saveRatingOfFilm(
        context: Context,
        film: Film,
        rating: Float
    )

    /**
     * Fetches the rating of [Film]
     * @param context   a [Context]
     * @param film      a [Film]
     * @return a [Float] that contains the rating value
     */
    fun fetchRatingOfFilm(
        context: Context,
        film: Film
    ): Float

    /**
     * Saves the comments of [Film]
     * @param context   a [Context]
     * @param film      a [Film]
     * @param comment   a [String] that contains the comment
     */
    fun saveCommentsOfFilm(
        context: Context,
        film: Film,
        comment: String
    )

    /**
     * Fetches the comments of [Film]
     * @param context   a [Context]
     * @param film      a [Film]
     * @return a [String] that contains the comment
     */
    fun fetchCommentsOfFilm(
        context: Context,
        film: Film
    ): String
}