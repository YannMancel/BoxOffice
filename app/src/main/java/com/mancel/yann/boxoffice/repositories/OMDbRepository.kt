package com.mancel.yann.boxoffice.repositories

import android.content.Context
import com.mancel.yann.boxoffice.apis.DummyBoxOffice
import com.mancel.yann.boxoffice.apis.OMDbService
import com.mancel.yann.boxoffice.models.Movie
import com.mancel.yann.boxoffice.utils.MapperTools
import com.mancel.yann.boxoffice.utils.SaveTools
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.repositories
 *
 * A class which implements [MovieRepository].
 */
class OMDbRepository : MovieRepository {

    // FIELDS --------------------------------------------------------------------------------------

    private val mOMDbService = OMDbService.retrofit
                                          .create(OMDbService::class.java)

    // METHODS -------------------------------------------------------------------------------------

    // -- Stream --

    override fun getStreamToFetchMovieByTitle(
        title: String,
        key: String,
        resultType: String,
        dataType: String,
        plot: String
    ): Single<Movie> {
        return this.mOMDbService.getFilmByTitle(title, key, resultType, dataType, plot)
                                .map { MapperTools.fromOMDbMovieToMovie(it) }
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .timeout(10L, TimeUnit.SECONDS)
    }

    override fun getStreamToFetchMovies(
        key: String,
        resultType: String,
        dataType: String,
        plot: String
    ): Single<List<Movie>> {
        return Observable.just(DummyBoxOffice.movieNames)
                         .flatMapIterable { it }
                         .flatMap {
                             this.getStreamToFetchMovieByTitle(it, key, resultType, dataType, plot)
                                 .toObservable()
                         }
                         .toList()
    }

    // -- Rating of film --

    override fun saveRatingOfMovie(context: Context, movie: Movie, rating: Float) =
        SaveTools.saveFloatIntoSharedPreferences(context, "${movie.id!!}-rate", rating)

    override fun fetchRatingOfMovie(context: Context, movie: Movie): Float =
        SaveTools.fetchFloatFromSharedPreferences(context, "${movie.id!!}-rate")

    // -- Comments of film --

    override fun saveCommentsOfMovie(context: Context, movie: Movie, comment: String) =
        SaveTools.saveStringIntoSharedPreferences(context, "${movie.id!!}-text", comment)

    override fun fetchCommentsOfMovie(context: Context, movie: Movie): String =
        SaveTools.fetchStringFromSharedPreferences(context, "${movie.id!!}-text")
}