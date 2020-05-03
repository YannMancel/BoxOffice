package com.mancel.yann.boxoffice.views.fragments

import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.models.Movie
import com.mancel.yann.boxoffice.utils.OMDbTools
import com.mancel.yann.boxoffice.utils.setTransitionCompat
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

    private var mMovie: Movie? = null
    private lateinit var mActorAdapter: ActorAdapter
    private lateinit var mSimilarMovieAdapter: SimilarMovieAdapter

    // METHODS -------------------------------------------------------------------------------------

    // -- BaseFragment --

    override fun getFragmentLayout(): Int = R.layout.fragment_details

    override fun configureDesign(savedInstanceState: Bundle?) {
        // Movie
        this.fetchMovieFromArgument()

        // My review
        this.fetchMyReview()

        // Transitions
        this.configureUIForTransition()

        // UI
        this.configureRecyclerViewForActors()
        this.configureRecyclerViewForSimilarMovies()
        this.configureUI()

        // LiveData
        this.configureMovieLiveData(savedInstanceState)
    }

    // -- Fragment --

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.configureSharedElementForTransition()
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
        // Movie from Tag
        val movie = v?.tag as? Movie

        // Convert Movie to Json
        val json = Moshi.Builder()
                        .build()
                        .adapter(Movie::class.java)
                        .toJson(movie)

        // Navigation by destination (Safe Args)
        val bundle = DetailsFragmentArgs(json).toBundle()
        this.findNavController().navigate(R.id.navigation_DetailsFragment, bundle)
    }

    // -- Transition --

    /**
     * Configures the Shared Element for the Transition between Fragment
     */
    private fun configureSharedElementForTransition() {
        val transition = TransitionInflater.from(this.requireContext())
                                           .inflateTransition(R.transition.move)

        this.sharedElementEnterTransition = transition
        this.sharedElementReturnTransition = transition
    }

    /**
     * Configures UI for Transition
     */
    private fun configureUIForTransition() {
        this.mMovie?.id?.let {
            this.mRootView.fragment_details_image.setTransitionCompat("image", this.mMovie?.id!!)
            this.mRootView.fragment_details_title.setTransitionCompat("title", this.mMovie?.id!!)
        }
    }

    // -- Movie --

    /**
     * Fetches the [Movie] from argument
     */
    private fun fetchMovieFromArgument() {
        this.mJsonItem?.let {
            // Convert Json to Film
            this.mMovie = Moshi.Builder()
                              .build()
                              .adapter(Movie::class.java)
                              .fromJson(it)
        }
    }

    // -- My review --

    /**
     * Fetches the user's review
     */
    private fun fetchMyReview() {
        this.mMovie?.id?.let {
            this.mRootView.fragment_details_my_review_rate.rating =
                this.mViewModel.fetchRatingOfMovie(
                    this.requireContext(),
                    this.mMovie!!
                )

            this.mRootView.fragment_details_my_review_text.editText?.text?.let {
                it.clear()
                it.append(
                    this.mViewModel.fetchCommentsOfMovie(
                        this.requireContext(),
                        this.mMovie!!
                    )
                )
            }
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
        this.mMovie?.let { movie ->
            // Title
            movie.title?.let { title ->
                this.mRootView.fragment_details_title.text = title
            }

            // Image
            movie.poster?.let { poster ->
                Glide.with(this@DetailsFragment)
                     .load(poster)
                     .centerCrop()
                     .error(R.drawable.ic_close)
                     .into(this.mRootView.fragment_details_image)
            }

            // Release
            movie.released?.let { release ->
                this.mRootView.fragment_details_release.text = release
            }

            // Critics
            movie.critics?.let { critics ->
                this.mRootView.fragment_details_critics.rating = critics.toFloat() * 5.0F / 100.0F
            }

            // Audience
            movie.audience?.let { audience ->
                this.mRootView.fragment_details_audience.rating = audience.toFloat() * 5.0F / 10.0F
            }

            // Title: Release
            this.mRootView.fragment_details_my_review_title.text = SpannableString(
                this.getString(R.string.my_review)
            ).apply {
                setSpan(UnderlineSpan(), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            // My preview
            movie.id?.let {
                this.mRootView.fragment_details_my_review_rate.setOnRatingBarChangeListener { _, rating, _ ->
                    // Save Rating
                    this.mViewModel.saveRatingOfMovie(
                        this.requireContext(),
                        movie = movie,
                        rating = rating
                    )

                    this.mCallback?.showMessage(this.getString(R.string.your_rating, rating))
                }

                this.mRootView.fragment_details_my_review_text.editText?.addTextChangedListener(
                    object : TextWatcher {

                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) { /* Do nothing */ }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) { /* Do nothing */ }

                        override fun afterTextChanged(s: Editable?) {
                            // Save Comments
                            this@DetailsFragment.mViewModel.saveCommentsOfMovie(
                                this@DetailsFragment.requireContext(),
                                movie = movie,
                                comment = s.toString()
                            )
                        }
                    }
                )
            }

            // Title: Synopsis
            this.mRootView.fragment_details_plot_title.text = SpannableString(
                this.getString(R.string.synopsis)
            ).apply {
                setSpan(UnderlineSpan(), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            // Synopsis
            movie.synopsis?.let { synopsis ->
                this.mRootView.fragment_details_plot.text = synopsis }

            // Title: Casting
            this.mRootView.fragment_details_casting_title.text = SpannableString(
                this.getString(R.string.casting)
            ).apply {
                setSpan(UnderlineSpan(), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            // Casting
            movie.actors?.let { casting ->
                this.mActorAdapter.updateData(OMDbTools.getData(casting))
            }

            // Title: Similar movies
            this.mRootView.fragment_details_similar_movies_title.text = SpannableString(
                this.getString(R.string.similar_movies)
            ).apply {
                setSpan(UnderlineSpan(), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    // -- LiveData --

    /**
     * Configures the LiveData
     * @param savedInstanceState a [Bundle] to check the configuration changes of [DetailsFragment]
     */
    private fun configureMovieLiveData(savedInstanceState: Bundle?) {
        this.mViewModel
            .getFilms()
            .observe(this.viewLifecycleOwner, Observer {
                val similarMovies = this.getSimilarMovies(it)

                Collections.sort(similarMovies, Movie.AZTitleComparator())

                this.mSimilarMovieAdapter.updateData(similarMovies)
            })

        if (savedInstanceState == null) {
            this.mViewModel.fetchMovies(this.requireContext())
        }
    }

    // -- Similar movies --

    /**
     * Gets the similar movies
     * @param movies a [List] of [Movie]
     * @return a [List] of [Movie] that is filtered by genre
     */
    private fun getSimilarMovies(movies: List<Movie>): List<Movie> {
        val genresOfSelectedFilm = OMDbTools.getData(this.mMovie?.genre ?: "")

        return movies.filter { movie ->
            var isSimilar = false
            val genres = OMDbTools.getData(movie.genre ?: "")

            genres.forEach { genre ->
                genresOfSelectedFilm.forEach {
                    if (genre == it) {
                        isSimilar = true
                    }
                }
            }

            isSimilar
        }
        .filter { movie ->
            // Remove the selected movie
            this.mMovie?.id != movie.id
        }
    }
}