package com.mancel.yann.boxoffice.views.fragments

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.liveDatas.FilmLiveData
import com.mancel.yann.boxoffice.models.Film
import com.mancel.yann.boxoffice.repositories.OMDbRepository
import com.mancel.yann.boxoffice.repositories.OMDbRepositoryImpl
import com.mancel.yann.boxoffice.views.adapters.AdapterCallback
import com.mancel.yann.boxoffice.views.adapters.FilmAdapter
import kotlinx.android.synthetic.main.fragment_list.view.*

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
    private lateinit var mFilmLiveData: FilmLiveData

    // METHODS -------------------------------------------------------------------------------------

    // -- BaseFragment --

    override fun getFragmentLayout(): Int = R.layout.fragment_list

    override fun configureDesign() {
        // UI
        this.configureRecyclerView()

        // Test
        mFilmLiveData = FilmLiveData().apply {
            observe(this@ListFragment.viewLifecycleOwner, Observer {
                this@ListFragment.mAdapter.updateData(it)
            })
        }

        val key = this.getString(R.string.omdb_key)

        val repository: OMDbRepository = OMDbRepositoryImpl()

        mFilmLiveData.getFilmsWithObservable(repository.getStreamToFetchFilms(key))
    }

    // -- AdapterCallback interface --

    override fun onDataChanged() {
        this.mRootView.fragment_list_no_data.visibility = if (this.mAdapter.itemCount == 0)
                                                              View.VISIBLE
                                                          else
                                                              View.GONE
    }

    override fun onClick(v: View?) {
        // Data from Tag
        (v?.tag as? Film)?.title?.let { this.mCallback?.showMessage(it) }

        // Navigation by action (Safe Args)
        val action = ListFragmentDirections.actionNavigationListFragmentToNavigationDetailsFragment()
        this.findNavController().navigate(action)
    }

    // -- RecyclerView --

    /**
     * Configures the [RecyclerView]
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
            adapter = mAdapter
        }
    }
}
