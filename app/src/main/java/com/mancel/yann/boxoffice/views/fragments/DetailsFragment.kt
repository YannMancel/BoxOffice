package com.mancel.yann.boxoffice.views.fragments

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.models.Film
import com.mancel.yann.boxoffice.utils.ActorTools
import com.mancel.yann.boxoffice.utils.SaveTools
import com.mancel.yann.boxoffice.views.adapters.ActorAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.fragment_details.view.*

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.fragments
 *
 * A [BaseFragment] subclass.
 */
class DetailsFragment : BaseFragment() {

    // METHODS -------------------------------------------------------------------------------------

    private val mJsonItem: String? by lazy {
        DetailsFragmentArgs.fromBundle(this.requireArguments()).jsonItem
    }

    private var mFilm: Film? = null
    private lateinit var mAdapter: ActorAdapter

    // METHODS -------------------------------------------------------------------------------------

    // -- BaseFragment --

    override fun getFragmentLayout(): Int = R.layout.fragment_details

    override fun configureDesign() {
        // Film
        this.fetchFilmFromArgument()

        // SharedPreferences
        this.fetchMyReviewFromSharedPreferences()

        // UI
        this.configureRecyclerView()
        this.configureUI()
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
     * Configures the RecyclerView
     */
    private fun configureRecyclerView() {
        // Adapter
        this.mAdapter = ActorAdapter(mCallback = null)

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
            adapter = mAdapter
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
                this.mAdapter.updateData(ActorTools.getActors(casting))
            }
        }
    }
}
