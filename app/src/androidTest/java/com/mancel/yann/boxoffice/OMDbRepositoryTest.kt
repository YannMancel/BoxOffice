package com.mancel.yann.boxoffice

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mancel.yann.boxoffice.apis.DummyBoxOffice
import com.mancel.yann.boxoffice.models.Movie
import com.mancel.yann.boxoffice.repositories.MovieRepository
import com.mancel.yann.boxoffice.repositories.OMDbRepository
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice
 *
 * An android test on [MovieRepository].
 */
@RunWith(AndroidJUnit4::class)
class OMDbRepositoryTest {

    // FIELDS --------------------------------------------------------------------------------------

    private val mRepository: MovieRepository = OMDbRepository()

    private val mKey = InstrumentationRegistry.getInstrumentation()
                                              .targetContext
                                              .resources
                                              .getString(R.string.omdb_key)

    // METHODS -------------------------------------------------------------------------------------

    @Test
    fun shouldFetchFilm() {
        // Get the movie's title
        val title = DummyBoxOffice.movieNames[0]

        // Creates Observable
        val observable = this.mRepository.getStreamToFetchMovieByTitle(title = title,
                                                                      key = this.mKey)

        // Creates Observer
        val observer = TestObserver<Movie>()

        // Creates Stream
        observable.subscribeWith(observer)
                  .assertNoErrors()
                  .assertNoTimeout()
                  .awaitTerminalEvent()

        // Fetches the result
        val movie = observer.values()[0]

        // Test: Response
        assertEquals(title, movie.title)
    }

    @Test
    fun shouldFetchAllFilms() {
        // Creates Observable
        val observable = this.mRepository.getStreamToFetchMovies(key = this.mKey)

        // Creates Observer
        val observer = TestObserver<List<Movie>>()

        // Creates Stream
        observable.subscribeWith(observer)
                  .assertNoErrors()
                  .assertNoTimeout()
                  .awaitTerminalEvent()

        // Fetches the result
        val movies = observer.values()[0]

        // Test: Response
        assertEquals(DummyBoxOffice.movieNames.size, movies.size)
    }
}