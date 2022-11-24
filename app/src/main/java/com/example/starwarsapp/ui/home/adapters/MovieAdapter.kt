package com.example.starwarsapp.ui.home.adapters

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

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.movieItem = moviesList[position]
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}
