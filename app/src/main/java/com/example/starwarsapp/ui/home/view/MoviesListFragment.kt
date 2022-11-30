package com.example.starwarsapp.ui.home.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsapp.R
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.databinding.FragmentMoviesListBinding
import com.example.starwarsapp.ui.home.adapters.MovieAdapter
import com.example.starwarsapp.ui.home.viewModel.MoviesListViewModel
import com.example.starwarsapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListFragment : Fragment(), MovieAdapter.IMovieListener {
    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesListViewModel by activityViewModels()

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
        binding.moviesRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.moviesRecycler.adapter = MovieAdapter(mutableListOf(), this)
        binding.errorContainer.visibility = View.GONE

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {

                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_filter, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.filter_by_release_date -> {
                            viewModel.filterByReleaseDate()
                            true
                        }
                        R.id.filter_by_movie_history -> {
                            viewModel.filterByHistory()
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )

        viewModel.moviesList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.errorContainer.visibility = View.VISIBLE
            } else {
                binding.errorContainer.visibility = View.GONE
                (binding.moviesRecycler.adapter as MovieAdapter).updateList(it.toMutableList())
            }
        }

        if (Utils.checkForInternet(binding.root.context)) {
            viewModel.getAllMovies()
        }
        viewModel.getAllMoviesLocally()
    }

    override fun onMovieTap(movie: MovieEntity) {
        viewModel.updateActiveMovie(movie)
    }
}
