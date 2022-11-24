package com.example.starwarsapp.ui.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.databinding.FragmentMoviesListBinding
import com.example.starwarsapp.ui.home.adapters.MovieAdapter

class MoviesListFragment : Fragment(), MovieAdapter.IMovieListener {
    private var _binding: FragmentMoviesListBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moviesRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRecycler.adapter = MovieAdapter(mutableListOf(), this)
    }

    override fun onMovieTap(movie: MovieEntity) {
    }
}
