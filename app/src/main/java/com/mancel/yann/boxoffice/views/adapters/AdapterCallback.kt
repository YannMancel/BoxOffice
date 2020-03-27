package com.mancel.yann.boxoffice.views.adapters

import android.view.View

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.adapters
 */
interface AdapterCallback {

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Called with the updateData method of adapter
     */
    fun onDataChanged()

    /**
     * Called when a view has been clicked.
     */
    fun onClick(v: View?)
}