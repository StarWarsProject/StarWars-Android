package com.example.starwarsapp.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.databinding.MovieItemBinding

class MovieAdapter(var moviesList: MutableList<MovieEntity>, val listener: IMovieListener) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    interface IMovieListener {
        fun onMovieTap(movie: MovieEntity)
    }

    class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        val movieViewHolder = MovieViewHolder(binding)
        return movieViewHolder
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.movieItem = moviesList[position]

        val uri = "@drawable/${moviesList[position].title.lowercase().split(" ").joinToString("_")}"
        val imageResource = holder.binding.root.context.resources.getIdentifier(uri, null, holder.binding.root.context.packageName)
        val imageDrawable = holder.binding.root.context.getDrawable(imageResource)
        holder.binding.ivMoviePoster.setImageDrawable(imageDrawable)
        holder.binding.movieContainer.setOnClickListener {
            listener.onMovieTap(moviesList[position])
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun updateList(moviesList: MutableList<MovieEntity>) {
        val oldSize = this.moviesList.size
        this.moviesList = moviesList
        if (moviesList.size <= oldSize) {
            notifyItemRangeRemoved(0, oldSize)
            notifyItemRangeInserted(0, moviesList.size)
        } else {
            notifyItemRangeChanged(0, this.moviesList.size)
        }
    }
}
