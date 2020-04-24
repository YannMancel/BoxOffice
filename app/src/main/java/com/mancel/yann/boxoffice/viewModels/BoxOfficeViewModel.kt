package com.mancel.yann.boxoffice.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.liveDatas.FilmLiveData
import com.mancel.yann.boxoffice.models.Film
import com.mancel.yann.boxoffice.repositories.FilmRepository

/**
 * Created by Yann MANCEL on 29/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.viewModels
 *
 * A [ViewModel] subclass.
 */
class BoxOfficeViewModel(
    private val mFilmRepository: FilmRepository
) : ViewModel() {

    // FIELDS --------------------------------------------------------------------------------------

    private var mFilms: FilmLiveData? = null

    // METHODS -------------------------------------------------------------------------------------

    // -- LiveData --

    /**
     * Get all [Film] from [FilmRepository]
     * @return a [LiveData] of [List] of [Film]
     */
    fun getFilms(): LiveData<List<Film>> {
        if (this.mFilms == null) {
            this.mFilms = FilmLiveData()
        }
        return this.mFilms!!
    }

    /**
     * Fetches the films from [FilmLiveData]
     * @param context a [Context]
     */
    fun fetchFilms(context: Context) {
        this.mFilms?.let {
            // key
            val key = context.getString(R.string.omdb_key)

            // Fetches data
            it.getFilmsWithSingle(this.mFilmRepository.getStreamToFetchFilms(key))
        }
    }

    // -- Rating of film --

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
    ) = this.mFilmRepository.saveRatingOfFilm(context, film, rating)

    /**
     * Fetches the rating of [Film]
     * @param context   a [Context]
     * @param film      a [Film]
     * @return a [Float] that contains the rating value
     */
    fun fetchRatingOfFilm(
        context: Context,
        film: Film
    ): Float = this.mFilmRepository.fetchRatingOfFilm(context, film)

    // -- Comments of film --

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
    ) = this.mFilmRepository.saveCommentsOfFilm(context, film, comment)

    /**
     * Fetches the comments of [Film]
     * @param context   a [Context]
     * @param film      a [Film]
     * @return a [String] that contains the comment
     */
    fun fetchCommentsOfFilm(
        context: Context,
        film: Film
    ): String = this.mFilmRepository.fetchCommentsOfFilm(context, film)
}