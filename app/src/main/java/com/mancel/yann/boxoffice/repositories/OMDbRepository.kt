package com.mancel.yann.boxoffice.repositories

import com.mancel.yann.boxoffice.models.Film
import io.reactivex.Observable

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.repositories
 */
interface OMDbRepository {

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Gets a stream to fetch the [Film] by its title
     * @param title         a [String] that contains the title
     * @param key           a [String] that contains your application's API key
     * @param resultType    a [String] that defines the type of result to return
     * @param dataType      a [String] that defines the data type to return
     * @param plot          a [String] that is either short or full plot
     * @return an [Observable] of [Film]
     */
    fun getStreamToFetchFilmByTitle(
        title: String,
        key: String,
        resultType: String = "movie",
        dataType: String = "json",
        plot: String = "short"
    ): Observable<Film>

    /**
     * Gets a stream to fetch the [List] of [Film]
     * @param key           a [String] that contains your application's API key
     * @param resultType    a [String] that defines the type of result to return
     * @param dataType      a [String] that defines the data type to return
     * @param plot          a [String] that is either short or full plot
     * @return an [Observable] of [List] of [Film]
     */
    fun getStreamToFetchFilms(
        key: String,
        resultType: String = "movie",
        dataType: String = "json",
        plot: String = "short"
    ): Observable<List<Film>>
}