package com.mancel.yann.boxoffice.liveDatas

import android.util.Log
import androidx.lifecycle.LiveData
import com.mancel.yann.boxoffice.models.Movie
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver

/**
 * Created by Yann MANCEL on 28/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.liveDatas
 *
 * A [LiveData] of [List] of [Movie] subclass.
 */
class MovieLiveData : LiveData<List<Movie>>() {

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

    // -- Movies --

    /**
     * Gets the [List] of [Movie] with [Single]
     * @param single a [Single] of [List] of [Movie]
     */
    fun getMoviesWithSingle(single: Single<List<Movie>>) {
        // Creates stream
        this.mDisposable = single.subscribeWith(object : DisposableSingleObserver<List<Movie>>() {

            override fun onSuccess(movies: List<Movie>) {
                // Notify
                this@MovieLiveData.value = movies
            }

            override fun onError(e: Throwable) {
                Log.e(this@MovieLiveData::class.simpleName, "${this@MovieLiveData::class.simpleName}: onError: ${e.message}")
            }
        })
    }
}