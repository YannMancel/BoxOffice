package com.mancel.yann.boxoffice.utils

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.utils
 *
 * A [DiffUtil.Callback] subclass.
 */
class ActorDiffCallback(
    private val mOldList: List<String>,
    private val mNewList: List<String>
) : DiffUtil.Callback() {

    // METHODS -------------------------------------------------------------------------------------

    override fun getOldListSize(): Int = this.mOldList.size

    override fun getNewListSize(): Int = this.mNewList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return this.mOldList[oldItemPosition] == this.mNewList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =  false
}