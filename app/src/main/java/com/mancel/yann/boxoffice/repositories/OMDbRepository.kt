package com.mancel.yann.boxoffice.repositories

import android.content.Context
import com.mancel.yann.boxoffice.apis.DummyBoxOffice
import com.mancel.yann.boxoffice.apis.OMDbService
import com.mancel.yann.boxoffice.models.Film
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
 * A class which implements [FilmRepository].
 */
class OMDbRepository : FilmRepository {

    // FIELDS --------------------------------------------------------------------------------------

    private val mOMDbService = OMDbService.retrofit
                                          .create(OMDbService::class.java)

    // METHODS -------------------------------------------------------------------------------------

    // -- Stream --

    override fun getStreamToFetchFilmByTitle(
        title: String,
        key: String,
        resultType: String,
        dataType: String,
        plot: String
    ): Single<Film> {
        return this.mOMDbService.getFilmByTitle(title, key, resultType, dataType, plot)
                                .map { MapperTools.fromOMDbFilmToFilm(it) }
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .timeout(10L, TimeUnit.SECONDS)
    }

    override fun getStreamToFetchFilms(
        key: String,
        resultType: String,
        dataType: String,
        plot: String
    ): Single<List<Film>> {
        return Observable.just(DummyBoxOffice.filmNames)
                         .flatMapIterable { it }
                         .flatMap {
                             this.getStreamToFetchFilmByTitle(it, key, resultType, dataType, plot)
                                 .toObservable()
                         }
                         .toList()
    }

    // -- Rating of film --

    override fun saveRatingOfFilm(context: Context, film: Film, rating: Float) =
        SaveTools.saveFloatIntoSharedPreferences(context, "${film.id!!}-rate", rating)

    override fun fetchRatingOfFilm(context: Context, film: Film): Float =
        SaveTools.fetchFloatFromSharedPreferences(context, "${film.id!!}-rate")

    // -- Comments of film --

    override fun saveCommentsOfFilm(context: Context, film: Film, comment: String) =
        SaveTools.saveStringIntoSharedPreferences(context, "${film.id!!}-text", comment)

    override fun fetchCommentsOfFilm(context: Context, film: Film): String =
        SaveTools.fetchStringFromSharedPreferences(context, "${film.id!!}-text")
}