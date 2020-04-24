package com.mancel.yann.boxoffice.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.utils.ActorDiffCallback
import kotlinx.android.synthetic.main.item_actor.view.*
import java.lang.ref.WeakReference

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.adapters
 *
 * A [RecyclerView.Adapter] subclass.
 */
class ActorAdapter(
    private val mCallback: AdapterCallback? = null
) : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    // FIELDS --------------------------------------------------------------------------------------

    private val mActors = mutableListOf<String>()

    // METHODS -------------------------------------------------------------------------------------

    // -- RecyclerView.Adapter --

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        // Creates the View thanks to the inflater
        val view = LayoutInflater.from(parent.context)
                                 .inflate(R.layout.item_actor, parent, false)

        return ActorViewHolder(view, WeakReference(this.mCallback))
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        this.configureDesign(holder, this.mActors[position])
    }

    override fun getItemCount(): Int = this.mActors.size

    // -- Design item --

    /**
     * Configures the design of each item
     * @param holder            a [ActorViewHolder] that corresponds to the item
     * @param fullNameOfActor   a [String] that contains the full name of the actor
     */
    private fun configureDesign(holder: ActorViewHolder, fullNameOfActor: String) {
        // Full name
        holder.itemView.item_actor_full_name.text = fullNameOfActor
    }

    // -- Actor --

    /**
     * Updates data of [ActorAdapter]
     * @param newActors a [List] of [String]
     */
    fun updateData(newActors: List<String>) {
        // Optimizes the performances of RecyclerView
        val diffCallback  = ActorDiffCallback(this.mActors, newActors)
        val diffResult  = DiffUtil.calculateDiff(diffCallback )

        // New data
        this.mActors.clear()
        this.mActors.addAll(newActors)

        // Notifies adapter
        diffResult.dispatchUpdatesTo(this@ActorAdapter)

        // Callback
        this.mCallback?.onDataChanged()
    }

    // NESTED CLASSES ------------------------------------------------------------------------------

    /**
     * A [RecyclerView.ViewHolder] subclass.
     */
    class ActorViewHolder(
        itemView: View,
        var mCallback: WeakReference<AdapterCallback?>
    ) : RecyclerView.ViewHolder(itemView)
}