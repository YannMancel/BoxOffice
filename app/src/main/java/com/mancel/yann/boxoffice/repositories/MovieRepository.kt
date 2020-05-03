package com.mancel.yann.boxoffice.repositories

import android.content.Context
import com.mancel.yann.boxoffice.models.Movie
import io.reactivex.Single

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.repositories
 */
interface MovieRepository {

    // METHODS -------------------------------------------------------------------------------------

    // -- Stream --

    /**
     * Gets a stream to fetch the [Movie] by its title
     * @param title         a [String] that contains the title
     * @param key           a [String] that contains your application's API key
     * @param resultType    a [String] that defines the type of result to return
     * @param dataType      a [String] that defines the data type to return
     * @param plot          a [String] that is either short or full plot
     * @return a [Single] of [Movie]
     */
    fun getStreamToFetchMovieByTitle(
        title: String,
        key: String,
        resultType: String = "movie",
        dataType: String = "json",
        plot: String = "short"
    ): Single<Movie>

    /**
     * Gets a stream to fetch the [List] of [Movie]
     * @param key           a [String] that contains your application's API key
     * @param resultType    a [String] that defines the type of result to return
     * @param dataType      a [String] that defines the data type to return
     * @param plot          a [String] that is either short or full plot
     * @return a [Single] of [List] of [Movie]
     */
    fun getStreamToFetchMovies(
        key: String,
        resultType: String = "movie",
        dataType: String = "json",
        plot: String = "short"
    ): Single<List<Movie>>

    // -- Data --

    /**
     * Saves the rating of [Movie]
     * @param context   a [Context]
     * @param movie     a [Movie]
     * @param rating    a [Float] that contains the rating value
     */
    fun saveRatingOfMovie(
        context: Context,
        movie: Movie,
        rating: Float
    )

    /**
     * Fetches the rating of [Movie]
     * @param context   a [Context]
     * @param movie     a [Movie]
     * @return a [Float] that contains the rating value
     */
    fun fetchRatingOfMovie(
        context: Context,
        movie: Movie
    ): Float

    /**
     * Saves the comments of [Movie]
     * @param context   a [Context]
     * @param movie     a [Movie]
     * @param comment   a [String] that contains the comment
     */
    fun saveCommentsOfMovie(
        context: Context,
        movie: Movie,
        comment: String
    )

    /**
     * Fetches the comments of [Movie]
     * @param context   a [Context]
     * @param movie     a [Movie]
     * @return a [String] that contains the comment
     */
    fun fetchCommentsOfMovie(
        context: Context,
        movie: Movie
    ): String
}