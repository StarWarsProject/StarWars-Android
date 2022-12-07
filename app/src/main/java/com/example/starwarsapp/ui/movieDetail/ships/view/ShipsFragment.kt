package com.example.starwarsapp.ui.movieDetail.ships.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsapp.databinding.FragmentShipsBinding
import com.example.starwarsapp.ui.movieDetail.adapters.StarshipAdapter
import com.example.starwarsapp.ui.movieDetail.viewModel.MovieDetailViewModel
import com.example.starwarsapp.ui.movieDetail.viewModel.TypeTabs

class ShipsFragment : Fragment() {
    private var _binding: FragmentShipsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipsBinding.inflate(inflater, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shipsRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.shipsRecycler.adapter = StarshipAdapter(listOf())
        binding.tvNotSynced.visibility = View.GONE
        binding.refreshContainer.setOnRefreshListener {
            viewModel.selectedMovie.value?.let {
                viewModel.refreshList(binding.root.context, it, TypeTabs.SHIPS)
            }
        }
        setObservers()
        viewModel.selectedMovie.value?.let {
            viewModel.syncList(binding.root.context, it, TypeTabs.SHIPS)
        }
    }
    private fun setObservers() {
        viewModel.starshipsList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                binding.errorContainer.container.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.errorContainer.container.visibility = View.GONE
                (binding.shipsRecycler.adapter as StarshipAdapter).updateList(list)
            }
        }
        viewModel.dataError.observe(viewLifecycleOwner) { hasError ->
            binding.progressBar.visibility = View.GONE
            if (hasError) {
                binding.errorContainer.container.visibility = View.VISIBLE
            } else {
                binding.errorContainer.container.visibility = View.GONE
            }
        }
        viewModel.syncError.observe(viewLifecycleOwner) { hasError ->
            binding.progressBar.visibility = View.GONE
            if (hasError) {
                binding.tvNotSynced.visibility = View.VISIBLE
            } else {
                binding.tvNotSynced.visibility = View.GONE
            }
            binding.refreshContainer.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
