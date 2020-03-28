package com.mancel.yann.boxoffice.repositories

import com.mancel.yann.boxoffice.apis.DummyBoxOffice
import com.mancel.yann.boxoffice.apis.OMDbService
import com.mancel.yann.boxoffice.models.Film
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.repositories
 *
 * A class which implements [OMDbRepository].
 */
class OMDbRepositoryImpl : OMDbRepository {

    // FIELDS --------------------------------------------------------------------------------------

    private val mOMDbService = OMDbService.retrofit
                                          .create(OMDbService::class.java)

    // METHODS -------------------------------------------------------------------------------------

    override fun getStreamToFetchFilmByTitle(
        title: String,
        key: String,
        resultType: String,
        dataType: String,
        plot: String
    ): Observable<Film> {
        return this.mOMDbService.getFilmByTitle(title, key, resultType, dataType, plot)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .timeout(10L, TimeUnit.SECONDS)
    }

    override fun getStreamToFetchFilms(
        key: String,
        resultType: String,
        dataType: String,
        plot: String
    ): Observable<List<Film>> {
        return Observable.just(DummyBoxOffice.filmNames)
                         .flatMapIterable { it }
                         .flatMap {
                             this.getStreamToFetchFilmByTitle(it, key, resultType, dataType, plot)
                         }
                         .toList()
                         .toObservable()
    }
}