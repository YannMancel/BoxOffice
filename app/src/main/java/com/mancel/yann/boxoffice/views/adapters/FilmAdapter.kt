package com.mancel.yann.boxoffice.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.models.Film
import com.mancel.yann.boxoffice.utils.FilmDiffCallback
import kotlinx.android.synthetic.main.item_film.view.*
import java.lang.ref.WeakReference

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.adapters
 *
 * A [RecyclerView.Adapter] subclass.
 */
class FilmAdapter(
    private val mCallback: AdapterCallback? = null
) : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    // FIELDS --------------------------------------------------------------------------------------

    private val mFilms = mutableListOf<Film>()

    // METHODS -------------------------------------------------------------------------------------

    // -- RecyclerView.Adapter --

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        // Creates the View thanks to the inflater
        val view = LayoutInflater.from(parent.context)
                                 .inflate(R.layout.item_film, parent, false)

        return FilmViewHolder(view, WeakReference(this.mCallback))
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        // Data
        val film = this.mFilms[position]

        // CardView: Listener
        holder.itemView.item_film_MaterialCardView.setOnClickListener {
            // Tag -> Film
            it.tag = film

            // Callback
            holder.mCallback.get()?.onClick(it)
        }

        // UI
        this.configureDesign(holder, film)
    }

    override fun getItemCount(): Int = this.mFilms.size

    // -- Design item --

    /**
     * Configures the design of each item
     * @param holder    a [FilmViewHolder] that corresponds to the item
     * @param film      a [Film]
     */
    private fun configureDesign(holder: FilmViewHolder, film: Film) {
        // Image
        film.poster?.let {
            Glide.with(holder.itemView)
                 .load(it)
                 .centerCrop()
                 .error(R.drawable.ic_close)
                 .into(holder.itemView.item_film_image)
        }

        // Title
        film.title?.let { holder.itemView.item_film_title.text = it }

        // Director
        film.director?.let { holder.itemView.item_film_director.text = it }
    }

    // -- Film --

    /**
     * Updates data of [FilmAdapter]
     * @param newFilms a [List] of [Film]
     */
    fun updateData(newFilms: List<Film>) {
        // Optimizes the performances of RecyclerView
        val diffCallback  = FilmDiffCallback(this.mFilms, newFilms)
        val diffResult  = DiffUtil.calculateDiff(diffCallback )

        // New data
        this.mFilms.clear()
        this.mFilms.addAll(newFilms)

        // Notifies adapter
        diffResult.dispatchUpdatesTo(this@FilmAdapter)

        // Callback
        this.mCallback?.onDataChanged()
    }

    // NESTED CLASSES ------------------------------------------------------------------------------

    /**
     * A [RecyclerView.ViewHolder] subclass.
     */
    class FilmViewHolder(
        itemView: View,
        var mCallback: WeakReference<AdapterCallback?>
    ) : RecyclerView.ViewHolder(itemView)
}