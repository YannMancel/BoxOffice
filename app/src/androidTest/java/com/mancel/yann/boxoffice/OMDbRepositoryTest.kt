package com.mancel.yann.boxoffice

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mancel.yann.boxoffice.apis.DummyBoxOffice
import com.mancel.yann.boxoffice.models.Film
import com.mancel.yann.boxoffice.repositories.OMDbRepository
import com.mancel.yann.boxoffice.repositories.OMDbRepositoryImpl
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice
 *
 * An android test on [OMDbRepository].
 */
@RunWith(AndroidJUnit4::class)
class OMDbRepositoryTest {

    // FIELDS --------------------------------------------------------------------------------------

    private val mRepository: OMDbRepository = OMDbRepositoryImpl()

    private val mKey = InstrumentationRegistry.getInstrumentation()
                                              .targetContext
                                              .resources
                                              .getString(R.string.omdb_key)

    // METHODS -------------------------------------------------------------------------------------

    @Test
    fun shouldFetchGuardiansOfTheGalaxyVol2() {
        // Creates Observable
        val observable = this.mRepository.getStreamToFetchFilmByTitle(title = "Guardians of the Galaxy Vol. 2",
                                                                      key = this.mKey)

        // Creates Observer
        val observer = TestObserver<Film>()

        // Creates Stream
        observable.subscribeWith(observer)
                  .assertNoErrors()
                  .assertNoTimeout()
                  .awaitTerminalEvent()

        // Fetches the result
        val film = observer.values()[0]

        // Test: Response
        assertEquals("True", film.response)
    }

    @Test
    fun shouldFetchAllFilms() {
        // Creates Observable
        val observable = this.mRepository.getStreamToFetchFilms(key = this.mKey)

        // Creates Observer
        val observer = TestObserver<List<Film>>()

        // Creates Stream
        observable.subscribeWith(observer)
            .assertNoErrors()
            .assertNoTimeout()
            .awaitTerminalEvent()

        // Fetches the result
        val films = observer.values()[0]

        // Test: Response
        assertEquals(DummyBoxOffice.filmNames.size, films.size)
        films.forEach { assertEquals("True", it.response) }
    }
}