package com.mancel.yann.boxoffice.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.liveDatas.FilmLiveData
import com.mancel.yann.boxoffice.models.Film
import com.mancel.yann.boxoffice.repositories.OMDbRepository

/**
 * Created by Yann MANCEL on 29/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.viewModels
 *
 * A [ViewModel] subclass.
 */
class BoxOfficeViewModel(
    private val mOMDbRepository: OMDbRepository
) : ViewModel() {

    // FIELDS --------------------------------------------------------------------------------------

    private var mFilms: FilmLiveData? = null

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Get all [Film] from [OMDbRepository]
     * @param context a [Context]
     * @return a [LiveData] of [List] of [Film]
     */
    fun getFilms(context: Context): LiveData<List<Film>> {

        if (this.mFilms == null) {
            this.mFilms = FilmLiveData()
        }

        // key
        val key = context.getString(R.string.omdb_key)

        // Fetches data
        this.mFilms!!.getFilmsWithObservable(this.mOMDbRepository.getStreamToFetchFilms(key))

        return this.mFilms!!
    }
}