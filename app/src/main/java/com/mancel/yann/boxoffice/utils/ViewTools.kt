package com.mancel.yann.boxoffice.utils

import android.view.View
import androidx.core.view.ViewCompat

/**
 * Created by Yann MANCEL on 02/04/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.utils
 */

fun View.setTransitionCompat(prefix: String, id: Any) {
    ViewCompat.setTransitionName(this, "$prefix-$id")
}

fun View.getTransitionCompat() = ViewCompat.getTransitionName(this) ?: ""