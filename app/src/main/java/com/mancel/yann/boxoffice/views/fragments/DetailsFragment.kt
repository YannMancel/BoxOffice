package com.mancel.yann.boxoffice.views.fragments

import com.bumptech.glide.Glide
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.models.Film
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

    // METHODS -------------------------------------------------------------------------------------

    // -- BaseFragment --

    override fun getFragmentLayout(): Int = R.layout.fragment_details

    override fun configureDesign() {
        // Film
        this.fetchFilmFromArgument()

        // UI
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

            // Synopsis
            film.plot?.let { synopsis ->
                this.mRootView.fragment_details_plot.text = synopsis }

            // Casting
            film.actors?.let { casting ->
                this.mRootView.fragment_details_actors.text = casting
            }
        }
    }
}
