package com.example.starwarsapp.ui.movieDetail.species.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsapp.databinding.FragmentSpeciesBinding
import com.example.starwarsapp.ui.movieDetail.adapters.SpecieAdapter
import com.example.starwarsapp.ui.movieDetail.viewModel.MovieDetailViewModel
import com.example.starwarsapp.ui.movieDetail.viewModel.TypeTabs

class SpeciesFragment : Fragment() {
    private var _binding: FragmentSpeciesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpeciesBinding.inflate(layoutInflater, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.speciesRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.speciesRecycler.adapter = SpecieAdapter(listOf())
        binding.tvNotSynced.visibility = View.GONE
        binding.errorContainer.container.visibility = View.GONE
        binding.refreshContainer.setOnRefreshListener {
            viewModel.selectedMovie.value?.let {
                viewModel.refreshList(binding.root.context, it, TypeTabs.SPECIES)
            }
        }
        binding.errorContainer.btnDismiss.setOnClickListener {
            binding.errorContainer.container.visibility = View.GONE
        }
        binding.tvNotSynced.setOnClickListener {
            binding.tvNotSynced.visibility = View.GONE
        }
        setObservers()
    }

    private fun setObservers() {
        viewModel.selectedMovie.observe(viewLifecycleOwner) {
            viewModel.syncList(binding.root.context, it, TypeTabs.SPECIES)
        }
        viewModel.speciesList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                binding.errorContainer.container.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.errorContainer.container.visibility = View.GONE
            }
            (binding.speciesRecycler.adapter as SpecieAdapter).updateList(list)
            binding.refreshContainer.isRefreshing = false
        }
        viewModel.dataError.observe(viewLifecycleOwner) { hasError ->
            if (hasError) {
                binding.errorContainer.container.visibility = View.VISIBLE
            } else {
                binding.errorContainer.container.visibility = View.GONE
            }
            binding.refreshContainer.isRefreshing = false
        }
        viewModel.syncError.observe(viewLifecycleOwner) { hasError ->
            if (hasError) {
                binding.tvNotSynced.visibility = View.VISIBLE
            } else {
                binding.tvNotSynced.visibility = View.GONE
            }
            binding.refreshContainer.isRefreshing = false
        }
    }
}
