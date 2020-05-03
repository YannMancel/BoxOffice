package com.mancel.yann.boxoffice.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.liveDatas.MovieLiveData
import com.mancel.yann.boxoffice.models.Movie
import com.mancel.yann.boxoffice.repositories.MovieRepository

/**
 * Created by Yann MANCEL on 29/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.viewModels
 *
 * A [ViewModel] subclass.
 */
class BoxOfficeViewModel(
    private val mMovieRepository: MovieRepository
) : ViewModel() {

    // FIELDS --------------------------------------------------------------------------------------

    private var mMovies: MovieLiveData? = null

    // METHODS -------------------------------------------------------------------------------------

    // -- LiveData --

    /**
     * Get all [Movie] from [MovieRepository]
     * @return a [LiveData] of [List] of [Movie]
     */
    fun getFilms(): LiveData<List<Movie>> {
        if (this.mMovies == null) {
            this.mMovies = MovieLiveData()
        }
        return this.mMovies!!
    }

    /**
     * Fetches the movies from [MovieLiveData]
     * @param context a [Context]
     */
    fun fetchMovies(context: Context) {
        this.mMovies?.let {
            // key
            val key = context.getString(R.string.omdb_key)

            // Fetches data
            it.getMoviesWithSingle(this.mMovieRepository.getStreamToFetchMovies(key))
        }
    }

    // -- Rating of movie --

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
    ) = this.mMovieRepository.saveRatingOfMovie(context, movie, rating)

    /**
     * Fetches the rating of [Movie]
     * @param context   a [Context]
     * @param movie     a [Movie]
     * @return a [Float] that contains the rating value
     */
    fun fetchRatingOfMovie(
        context: Context,
        movie: Movie
    ): Float = this.mMovieRepository.fetchRatingOfMovie(context, movie)

    // -- Comments of film --

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
    ) = this.mMovieRepository.saveCommentsOfMovie(context, movie, comment)

    /**
     * Fetches the comments of [Movie]
     * @param context   a [Context]
     * @param movie     a [Movie]
     * @return a [String] that contains the comment
     */
    fun fetchCommentsOfMovie(
        context: Context,
        movie: Movie
    ): String = this.mMovieRepository.fetchCommentsOfMovie(context, movie)
}