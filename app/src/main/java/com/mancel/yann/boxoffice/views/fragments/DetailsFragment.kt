package com.mancel.yann.boxoffice.views.fragments

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.models.Film
import com.mancel.yann.boxoffice.utils.OMDbTools
import com.mancel.yann.boxoffice.utils.SaveTools
import com.mancel.yann.boxoffice.views.adapters.ActorAdapter
import com.mancel.yann.boxoffice.views.adapters.AdapterCallback
import com.mancel.yann.boxoffice.views.adapters.SimilarMovieAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.fragment_details.view.*
import java.util.*

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.fragments
 *
 * A [BaseFragment] subclass implements [AdapterCallback].
 */
class DetailsFragment : BaseFragment(), AdapterCallback {

    // METHODS -------------------------------------------------------------------------------------

    private val mJsonItem: String? by lazy {
        DetailsFragmentArgs.fromBundle(this.requireArguments()).jsonItem
    }

    private var mFilm: Film? = null
    private lateinit var mActorAdapter: ActorAdapter
    private lateinit var mSimilarMovieAdapter: SimilarMovieAdapter

    // METHODS -------------------------------------------------------------------------------------

    // -- BaseFragment --

    override fun getFragmentLayout(): Int = R.layout.fragment_details

    override fun configureDesign() {
        // Film
        this.fetchFilmFromArgument()

        // SharedPreferences
        this.fetchMyReviewFromSharedPreferences()

        // UI
        this.configureRecyclerViewForActors()
        this.configureRecyclerViewForSimilarMovies()
        this.configureUI()

        // LiveData
        this.configureFilmLiveData()
    }

    // -- AdapterCallback interface --

    override fun onDataChanged() {
        this.mRootView.fragment_details_no_similar_movies.visibility =
            if (this.mSimilarMovieAdapter.itemCount == 0)
                View.VISIBLE
            else
                View.GONE
    }

    override fun onClick(v: View?) {
        // Film from Tag
        val film = v?.tag as? Film

        // Convert Film to Json
        val json = Moshi.Builder()
                        .build()
                        .adapter(Film::class.java)
                        .toJson(film)

        // Navigation by destination (Safe Args)
        val bundle = DetailsFragmentArgs(json).toBundle()
        this.findNavController().navigate(R.id.navigation_DetailsFragment, bundle)
    }

    // -- Film --

    /**
     * Fetches the [Film] from argument
     */
    private fun fetchFilmFromArgument() {
        this.mJsonItem?.let {
            // Convert Json to Film
            this.mFilm = Moshi.Builder()
                              .build()
                              .adapter(Film::class.java)
                              .fromJson(it)
        }
    }

    // -- My review --

    /**
     * Fetches the user's rating from SharedPreferences
     */
    private fun fetchMyReviewFromSharedPreferences() {
        this.mFilm?.imdbID?.let { id ->
            this.mRootView.fragment_details_my_review.rating =
                SaveTools.fetchFloatFromSharedPreferences(this.requireContext(), id)
        }
    }

    // -- RecyclerView --

    /**
     * Configures the RecyclerView for the actors
     */
    private fun configureRecyclerViewForActors() {
        // Adapter
        this.mActorAdapter = ActorAdapter(mCallback = null)

        // LayoutManager
        val viewManager = LinearLayoutManager(this.requireContext(),
                                              LinearLayoutManager.HORIZONTAL,
                                              false)

        // Divider
        val divider = DividerItemDecoration(this.requireContext(),
                                            DividerItemDecoration.HORIZONTAL)

        // RecyclerView
        with(this.mRootView.fragment_details_casting) {
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(divider)
            adapter = this@DetailsFragment.mActorAdapter
        }
    }

    /**
     * Configures the RecyclerView for the similar movies
     */
    private fun configureRecyclerViewForSimilarMovies() {
        // Adapter
        this.mSimilarMovieAdapter = SimilarMovieAdapter(mCallback = this@DetailsFragment)

        // LayoutManager
        val viewManager = LinearLayoutManager(this.requireContext(),
                                              LinearLayoutManager.HORIZONTAL,
                                              false)

        // Divider
        val divider = DividerItemDecoration(this.requireContext(),
                                            DividerItemDecoration.HORIZONTAL)

        // RecyclerView
        with(this.mRootView.fragment_details_similar_movies) {
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(divider)
            adapter = this@DetailsFragment.mSimilarMovieAdapter
        }
    }

    // -- UI --

    /**
     * Configures UI
     */
    private fun configureUI() {
        this.mFilm?.let { film ->
            // Title
            film.title?.let { title ->
                this.mRootView.fragment_details_title.text = title
            }

            // Image
            film.poster?.let { poster ->
                Glide.with(this@DetailsFragment)
                     .load(poster)
                     .centerCrop()
                     .error(R.drawable.ic_close)
                     .into(this.mRootView.fragment_details_image)
            }

            // Release
            film.released?.let { release ->
                this.mRootView.fragment_details_release.text = release
            }

            // Critics
            film.metascore?.let { critics ->
                this.mRootView.fragment_details_critics.rating = critics.toFloat() * 5.0F / 100.0F
            }

            // Audience
            film.imdbRating?.let { audience ->
                this.mRootView.fragment_details_audience.rating = audience.toFloat() * 5.0F / 10.0F
            }

            // My preview
            film.imdbID?.let { id ->
                this.mRootView.fragment_details_my_review.setOnRatingBarChangeListener { _, rating, _ ->
                    // Rating into SharedPreferences
                    SaveTools.saveFloatIntoSharedPreferences(this.requireContext(), id, rating)

                    this.mCallback?.showMessage(this.getString(R.string.your_rating, rating))
                }
            }

            // Synopsis
            film.plot?.let { synopsis ->
                this.mRootView.fragment_details_plot.text = synopsis }

            // Casting
            film.actors?.let { casting ->
                this.mActorAdapter.updateData(OMDbTools.getData(casting))
            }
        }
    }

    // -- LiveData --

    /**
     * Configures the LiveData
     */
    private fun configureFilmLiveData() {
        this.mViewModel.getFilms(this.requireContext())
            .observe(this.viewLifecycleOwner, Observer {
                val similarMovies = this.getSimilarMovies(it)

                Collections.sort(similarMovies, Film.AZTitleComparator())

                this.mSimilarMovieAdapter.updateData(similarMovies)
            })
    }

    // -- Similar movies --

    /**
     * Gets the similar movies
     * @param movies a [List] of [Film]
     * @return a [List] of [Film] that is filtered by genre
     */
    private fun getSimilarMovies(movies: List<Film>): List<Film> {
        val genresOfSelectedFilm = OMDbTools.getData(this.mFilm?.genre ?: "")

        return movies.filter { film ->
            var isSimilar = false
            val genres = OMDbTools.getData(film.genre ?: "")

            genres.forEach { genre ->
                genresOfSelectedFilm.forEach {
                    if (genre == it) {
                        isSimilar = true
                    }
                }
            }

            isSimilar
        }
        .filter { film ->
            // Remove the selected film
            this.mFilm?.imdbID != film.imdbID
        }
    }
}
