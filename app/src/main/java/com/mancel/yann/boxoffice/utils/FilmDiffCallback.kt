package com.mancel.yann.boxoffice.utils

import androidx.recyclerview.widget.DiffUtil
import com.mancel.yann.boxoffice.models.Film

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.utils
 *
 * A [DiffUtil.Callback] subclass.
 */
class FilmDiffCallback(
    private val mOldList: List<Film>,
    private val mNewList: List<Film>
) : DiffUtil.Callback() {

    // METHODS -------------------------------------------------------------------------------------

    override fun getOldListSize(): Int = this.mOldList.size

    override fun getNewListSize(): Int = this.mNewList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Comparison based on Id:
        val oldId = this.mOldList[oldItemPosition].imdbID
        val newId = this.mNewList[newItemPosition].imdbID

        return oldId == newId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Comparison on all fields
        return this.mOldList[oldItemPosition] == this.mNewList[newItemPosition]
    }
}