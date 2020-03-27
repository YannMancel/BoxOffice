package com.mancel.yann.boxoffice.views.fragments

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.fragments
 */
interface FragmentCallback {

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Called from fragment to activity
     */
    fun showMessage(message: String)
}