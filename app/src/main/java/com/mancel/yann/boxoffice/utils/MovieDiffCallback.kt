package com.mancel.yann.boxoffice.utils

import androidx.recyclerview.widget.DiffUtil
import com.mancel.yann.boxoffice.models.Movie

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.utils
 *
 * A [DiffUtil.Callback] subclass.
 */
class MovieDiffCallback(
    private val mOldList: List<Movie>,
    private val mNewList: List<Movie>
) : DiffUtil.Callback() {

    // METHODS -------------------------------------------------------------------------------------

    override fun getOldListSize(): Int = this.mOldList.size

    override fun getNewListSize(): Int = this.mNewList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Comparison based on Id:
        val oldId = this.mOldList[oldItemPosition].id
        val newId = this.mNewList[newItemPosition].id

        return oldId == newId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Comparison on all fields
        return this.mOldList[oldItemPosition] == this.mNewList[newItemPosition]
    }
}