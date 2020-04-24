package com.mancel.yann.boxoffice.liveDatas

import android.util.Log
import androidx.lifecycle.LiveData
import com.mancel.yann.boxoffice.models.Film
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver

/**
 * Created by Yann MANCEL on 28/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.liveDatas
 *
 * A [LiveData] of [List] of [Film] subclass.
 */
class FilmLiveData : LiveData<List<Film>>() {

    // FIELDS --------------------------------------------------------------------------------------

    private var mDisposable: Disposable? = null

    // METHODS -------------------------------------------------------------------------------------

    // -- LiveData --

    override fun onInactive() {
        super.onInactive()

        // Disposes the Disposable
        this.mDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    // -- Films --

    /**
     * Gets the [List] of [Film] with [Single]
     * @param single an [Single] of [List] of [Film]
     */
    fun getFilmsWithSingle(single: Single<List<Film>>) {
        // Creates stream
        this.mDisposable = single.subscribeWith(object : DisposableSingleObserver<List<Film>>() {

            override fun onSuccess(films: List<Film>) {
                // Notify
                this@FilmLiveData.value = films
            }

            override fun onError(e: Throwable) {
                Log.e(this@FilmLiveData::class.simpleName, "${this@FilmLiveData::class.simpleName}: onError: ${e.message}")
            }
        })
    }
}