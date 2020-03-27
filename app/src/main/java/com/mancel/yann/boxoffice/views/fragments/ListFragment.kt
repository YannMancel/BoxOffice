package com.mancel.yann.boxoffice.views.fragments

import com.mancel.yann.boxoffice.R

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.fragments
 *
 * A [BaseFragment] subclass.
 */
class ListFragment : BaseFragment() {

    // METHODS -------------------------------------------------------------------------------------

    // -- BaseFragment --

    override fun getFragmentLayout(): Int = R.layout.fragment_list

    override fun configureDesign() {}
}
