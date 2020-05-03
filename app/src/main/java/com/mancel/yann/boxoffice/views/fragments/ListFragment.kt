package com.mancel.yann.boxoffice.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.models.Movie
import com.mancel.yann.boxoffice.utils.getTransitionCompat
import com.mancel.yann.boxoffice.views.adapters.AdapterCallback
import com.mancel.yann.boxoffice.views.adapters.MovieAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.fragment_list.view.*
import java.util.*

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.fragments
 *
 * A [BaseFragment] subclass which implements [AdapterCallback].
 */
class ListFragment : BaseFragment(), AdapterCallback {

    // FIELDS --------------------------------------------------------------------------------------

    private lateinit var mAdapter: MovieAdapter

    // METHODS -------------------------------------------------------------------------------------

    // -- BaseFragment --

    override fun getFragmentLayout(): Int = R.layout.fragment_list

    override fun configureDesign(savedInstanceState: Bundle?) {
        // UI
        this.configureRecyclerView()
        this.configureSwipeRefreshLayout()

        // LiveData
        this.configureMovieLiveData(savedInstanceState)
    }

    override fun syncData() = this.mViewModel.fetchMovies(this.requireContext())

    override fun searchData(newText: String) = this.mAdapter.filter.filter(newText)

    // -- AdapterCallback interface --

    override fun onDataChanged() {
        this.mRootView.fragment_list_no_data.visibility =
            if (this.mAdapter.itemCount == 0)
                View.VISIBLE
            else
                View.GONE
    }

    override fun onClick(v: View?) {
        // Event for the parent activity
        this.mCallback?.navigationEvent()

        // Movie from Tag
        val movie = v?.tag as? Movie

        // Convert Movie to Json
        val json = Moshi.Builder()
                        .build()
                        .adapter(Movie::class.java)
                        .toJson(movie)

        // Extra
        val imageForTransition = v?.findViewById<ImageView>(R.id.item_film_image)
        val titleForTransition = v?.findViewById<TextView>(R.id.item_film_title)

        val extras = FragmentNavigatorExtras(
            imageForTransition!! to imageForTransition.getTransitionCompat(),
            titleForTransition!! to titleForTransition.getTransitionCompat()
        )

        // Navigation by action (Safe Args)
        val action = ListFragmentDirections.actionNavigationListFragmentToNavigationDetailsFragment(json)
        this.findNavController().navigate(action, extras)
    }

    // -- RecyclerView --

    /**
     * Configures the RecyclerView
     */
    private fun configureRecyclerView() {
        // Adapter
        this.mAdapter = MovieAdapter(mCallback = this@ListFragment)

        // LayoutManager
        val viewManager = LinearLayoutManager(this.requireContext())

        // Divider
        val divider = DividerItemDecoration(this.requireContext(),
                                            DividerItemDecoration.VERTICAL)

        // RecyclerView
        with(this.mRootView.fragment_list_RecyclerView) {
            setHasFixedSize(true)
            layoutManager = viewManager
            addItemDecoration(divider)
            adapter = this@ListFragment.mAdapter
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    // -- SwipeRefreshLayout --

    /**
     * Configure the SwipeRefreshLayout
     */
    private fun configureSwipeRefreshLayout() {
        this.mRootView.fragment_list_SwipeRefreshLayout.setOnRefreshListener {
            this.mViewModel.fetchMovies(this.requireContext())
        }
    }

    // -- LiveData --

    /**
     * Configures the LiveData
     * @param savedInstanceState a [Bundle] to check the configuration changes of [ListFragment]
     */
    private fun configureMovieLiveData(savedInstanceState: Bundle?) {
        this.mViewModel
            .getFilms()
            .observe(this.viewLifecycleOwner, Observer {
                // Stops the animation of SwipeRefreshLayout
                if (this.mRootView.fragment_list_SwipeRefreshLayout.isRefreshing) {
                    this.mRootView.fragment_list_SwipeRefreshLayout.isRefreshing = false
                }

                // Sorts the list on its title from A to Z
                Collections.sort(it, Movie.AZTitleComparator())
                
                this.mAdapter.updateData(it)
            })

        if (savedInstanceState == null) {
            this.mViewModel.fetchMovies(this.requireContext())
        }
    }
}