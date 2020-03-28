package com.mancel.yann.boxoffice.views.fragments

import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.models.Film
import com.squareup.moshi.Moshi

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

    }
}
