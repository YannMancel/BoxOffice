package com.mancel.yann.boxoffice.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.utils
 */
object MessageTools {

    /**
     * Shows a [Snackbar] with a message
     * @param view      a [View] that will display the message
     * @param message   a [String] that contains the message to display
     */
    fun showMessageWithSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .show()
    }
}