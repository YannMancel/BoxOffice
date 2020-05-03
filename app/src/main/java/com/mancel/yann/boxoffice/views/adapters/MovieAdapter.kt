package com.mancel.yann.boxoffice.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mancel.yann.boxoffice.R
import com.mancel.yann.boxoffice.models.Movie
import com.mancel.yann.boxoffice.utils.MovieDiffCallback
import com.mancel.yann.boxoffice.utils.setTransitionCompat
import kotlinx.android.synthetic.main.item_film.view.*
import java.lang.ref.WeakReference

/**
 * Created by Yann MANCEL on 27/03/2020.
 * Name of the project: BoxOffice
 * Name of the package: com.mancel.yann.boxoffice.views.adapters
 *
 * A [RecyclerView.Adapter] subclass which implements [Filterable].
 */
class MovieAdapter(
    private val mCallback: AdapterCallback? = null
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(), Filterable {

    // ENUMS ---------------------------------------------------------------------------------------

    enum class DisplayMode {NORMAL_MODE, FILTER_MODE}

    // FIELDS --------------------------------------------------------------------------------------

    private val mMovies = mutableListOf<Movie>()
    private val mMoviesAll = mutableListOf<Movie>()

    private val mFilter = object : Filter() {

        // Run in Background Thread
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredMovies = mutableListOf<Movie>()

            constraint?.let { charSequence ->
                if (charSequence.toString().isEmpty()) {
                    filteredMovies.addAll(this@MovieAdapter.mMoviesAll)
                }
                else {
                    this@MovieAdapter.mMoviesAll.forEach { movie ->
                        movie.title?.let { title ->
                            if (title.contains(charSequence.toString(), ignoreCase = true)) {
                                filteredMovies.add(movie)
                            }
                        }
                    }
                }
            }

            return FilterResults().apply {
                values = filteredMovies
            }
        }

        // Run in UI Thread
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            results?.let {
                val filteredMovies = (it.values as List<*>).filterIsInstance<Movie>()
                this@MovieAdapter.updateData(filteredMovies, DisplayMode.FILTER_MODE)
            }
        }
    }

    // METHODS -------------------------------------------------------------------------------------

    // -- RecyclerView.Adapter --

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Creates the View thanks to the inflater
        val view = LayoutInflater.from(parent.context)
                                 .inflate(R.layout.item_film, parent, false)

        return MovieViewHolder(view, WeakReference(this.mCallback))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        // Data
        val movie = this.mMovies[position]

        // Transition
        movie.id?.let {
            holder.itemView.item_film_image.setTransitionCompat("image", movie.id!!)
            holder.itemView.item_film_title.setTransitionCompat("title", movie.id!!)
        }

        // CardView: Listener
        holder.itemView.item_film_MaterialCardView.setOnClickListener {
            // Tag -> Film
            holder.itemView.tag = movie

            // Callback
            holder.mCallback.get()?.onClick(holder.itemView)
        }

        // UI
        this.configureDesign(holder, movie)
    }

    override fun getItemCount(): Int = this.mMovies.size

    // -- Filterable interface --

    override fun getFilter(): Filter = this.mFilter

    // -- Design item --

    /**
     * Configures the design of each item
     * @param holder    a [MovieViewHolder] that corresponds to the item
     * @param movie     a [Movie]
     */
    private fun configureDesign(holder: MovieViewHolder, movie: Movie) {
        // Image
        movie.poster?.let {
            Glide.with(holder.itemView)
                 .load(it)
                 .centerCrop()
                 .error(R.drawable.ic_close)
                 .into(holder.itemView.item_film_image)
        }

        // Title
        movie.title?.let { holder.itemView.item_film_title.text = it }

        // Director
        movie.director?.let { holder.itemView.item_film_director.text = it }
    }

    // -- Movie --

    /**
     * Updates data of [MovieAdapter]
     * @param newMovies     a [List] of [Movie]
     * @param displayMode   a [DisplayMode]
     */
    fun updateData(
        newMovies: List<Movie>,
        displayMode: DisplayMode = DisplayMode.NORMAL_MODE
    ) {
        // Optimizes the performances of RecyclerView
        val diffCallback  = MovieDiffCallback(this.mMovies, newMovies)
        val diffResult  = DiffUtil.calculateDiff(diffCallback )

        // New data
        this.mMovies.clear()
        this.mMovies.addAll(newMovies)

        if (displayMode == DisplayMode.NORMAL_MODE) {
            this.mMoviesAll.clear()
            this.mMoviesAll.addAll(this.mMovies)
        }

        // Notifies adapter
        diffResult.dispatchUpdatesTo(this@MovieAdapter)

        // Callback
        this.mCallback?.onDataChanged()
    }

    // NESTED CLASSES ------------------------------------------------------------------------------

    /**
     * A [RecyclerView.ViewHolder] subclass.
     */
    class MovieViewHolder(
        itemView: View,
        var mCallback: WeakReference<AdapterCallback?>
    ) : RecyclerView.ViewHolder(itemView)
}