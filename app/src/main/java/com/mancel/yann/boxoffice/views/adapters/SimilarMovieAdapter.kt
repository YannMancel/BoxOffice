package com.mancel.yann.boxoffice.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.models.Movie
import com.mancel.yann.boxoffice.utils.MovieDiffCallback
import kotlinx.android.synthetic.main.item_poster_film.view.*
import java.lang.ref.WeakReference

/**
 * Created by Yann MANCEL on 30/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.adapters
 *
 * A [RecyclerView.Adapter] subclass.
 */
class SimilarMovieAdapter(
    private val mCallback: AdapterCallback? = null
) : RecyclerView.Adapter<SimilarMovieAdapter.SimilarMovieHolder>() {

    // FIELDS --------------------------------------------------------------------------------------

    private val mMovies = mutableListOf<Movie>()

    // METHODS -------------------------------------------------------------------------------------

    // -- RecyclerView.Adapter --

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieHolder {
        // Creates the View thanks to the inflater
        val view = LayoutInflater.from(parent.context)
                                 .inflate(R.layout.item_poster_film, parent, false)

        return SimilarMovieHolder(view, WeakReference(this.mCallback))
    }

    override fun onBindViewHolder(holder: SimilarMovieHolder, position: Int) {
        // Data
        val movie = this.mMovies[position]

        // CardView: Listener
        holder.itemView.item_poster_film_CardView.setOnClickListener {
            // Tag -> movie
            it.tag = movie

            // Callback
            holder.mCallback.get()?.onClick(it)
        }

        // UI
        this.configureDesign(holder, movie)
    }

    override fun getItemCount(): Int = this.mMovies.size

    // -- Design item --

    /**
     * Configures the design of each item
     * @param holder    a [SimilarMovieHolder] that corresponds to the item
     * @param movie     a [Movie]
     */
    private fun configureDesign(holder: SimilarMovieHolder, movie: Movie) {
        // Image
        movie.poster?.let {
            Glide.with(holder.itemView)
                 .load(it)
                 .centerCrop()
                 .error(R.drawable.ic_close)
                 .into(holder.itemView.item_poster_film_image)
        }

        // Title
        movie.title?.let { holder.itemView.item_poster_film_title.text = it }
    }

    // -- Film --

    /**
     * Updates data of [SimilarMovieAdapter]
     * @param newMovies a [List] of [Movie]
     */
    fun updateData(newMovies: List<Movie>) {
        // Optimizes the performances of RecyclerView
        val diffCallback  = MovieDiffCallback(this.mMovies, newMovies)
        val diffResult  = DiffUtil.calculateDiff(diffCallback )

        // New data
        this.mMovies.clear()
        this.mMovies.addAll(newMovies)

        // Notifies adapter
        diffResult.dispatchUpdatesTo(this@SimilarMovieAdapter)

        // Callback
        this.mCallback?.onDataChanged()
    }

    // NESTED CLASSES ------------------------------------------------------------------------------

    /**
     * A [RecyclerView.ViewHolder] subclass.
     */
    class SimilarMovieHolder(
        itemView: View,
        var mCallback: WeakReference<AdapterCallback?>
    ) : RecyclerView.ViewHolder(itemView)
}