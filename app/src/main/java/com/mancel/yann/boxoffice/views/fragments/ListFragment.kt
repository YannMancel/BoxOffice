package com.mancel.yann.boxoffice.views.fragments

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.models.Film
import com.mancel.yann.boxoffice.views.adapters.AdapterCallback
import com.mancel.yann.boxoffice.views.adapters.FilmAdapter
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

    private lateinit var mAdapter: FilmAdapter

    // METHODS -------------------------------------------------------------------------------------

    // -- BaseFragment --

    override fun getFragmentLayout(): Int = R.layout.fragment_list

    override fun configureDesign() {
        // UI
        this.configureRecyclerView()
        this.configureSwipeRefreshLayout()

        // LiveData
        this.configureFilmLiveData()
    }

    override fun syncData() = this.mViewModel.fetchFilms(this.requireContext())

    override fun searchData(newText: String) = this.mAdapter.filter.filter(newText)

    // -- AdapterCallback interface --

    override fun onDataChanged() {
        this.mRootView.fragment_list_no_data.visibility = if (this.mAdapter.itemCount == 0)
                                                              View.VISIBLE
                                                          else
                                                              View.GONE
    }

    override fun onClick(v: View?) {
        // Event for the parent activity
        this.mCallback?.navigationEvent()

        // Film from Tag
        val film = v?.tag as? Film

        // Convert Film to Json
        val json = Moshi.Builder()
                        .build()
                        .adapter(Film::class.java)
                        .toJson(film)

        // Navigation by action (Safe Args)
        val action = ListFragmentDirections.actionNavigationListFragmentToNavigationDetailsFragment(json)
        this.findNavController().navigate(action)
    }

    // -- RecyclerView --

    /**
     * Configures the RecyclerView
     */
    private fun configureRecyclerView() {
        // Adapter
        this.mAdapter = FilmAdapter(mCallback = this@ListFragment)

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
        }
    }

    // -- SwipeRefreshLayout --

    /**
     * Configure the SwipeRefreshLayout
     */
    private fun configureSwipeRefreshLayout() {
        this.mRootView.fragment_list_SwipeRefreshLayout.setOnRefreshListener {
            this.mViewModel.fetchFilms(this.requireContext())
        }
    }

    // -- LiveData --

    /**
     * Configures the LiveData
     */
    private fun configureFilmLiveData() {
        this.mViewModel.getFilms(this.requireContext())
            .observe(this.viewLifecycleOwner, Observer {
                // Stops the animation of SwipeRefreshLayout
                if (this.mRootView.fragment_list_SwipeRefreshLayout.isRefreshing) {
                    this.mRootView.fragment_list_SwipeRefreshLayout.isRefreshing = false
                }

                // Sorts the list on its title from A to Z
                Collections.sort(it, Film.AZTitleComparator())
                
                this.mAdapter.updateData(it)
            })
    }
}