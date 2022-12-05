package com.example.starwarsapp.ui.home.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsapp.R
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.databinding.FragmentMoviesListBinding
import com.example.starwarsapp.ui.home.adapters.MovieAdapter
import com.example.starwarsapp.ui.home.viewModel.MoviesListViewModel
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
        binding.errorContainer.container.visibility = View.GONE

        binding.ibFilter.setOnClickListener {
            showPopup(it)
        }

        viewModel.moviesList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.errorContainer.container.visibility = View.VISIBLE
            } else {
                binding.errorContainer.container.visibility = View.GONE
                (binding.moviesRecycler.adapter as MovieAdapter).updateList(it.toMutableList())
            }
        }

        viewModel.getAllMovies(binding.root.context)
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(requireContext(), v, Gravity.END)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_filter, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.filter_by_release_date -> {
                    viewModel.filterByReleaseDate()
                }
                R.id.filter_by_movie_history -> {
                    viewModel.filterByHistory()
                }
            }
            true
        }
        popup.show()
    }

    override fun onMovieTap(movie: MovieEntity) {
        viewModel.updateActiveMovie(movie)
    }
}
