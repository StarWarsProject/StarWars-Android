package com.example.starwarsapp.ui.movieDetail.planets.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsapp.databinding.FragmentPlanetsBinding
import com.example.starwarsapp.ui.movieDetail.adapters.PlanetAdapter
import com.example.starwarsapp.ui.movieDetail.viewModel.MovieDetailViewModel
import com.example.starwarsapp.ui.movieDetail.viewModel.TypeTabs

class PlanetsFragment : Fragment() {
    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetsBinding.inflate(layoutInflater, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.planetsRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.planetsRecycler.adapter = PlanetAdapter(listOf())
        binding.tvNotSynced.visibility = View.GONE
        binding.errorContainer.container.visibility = View.GONE
        binding.refreshContainer.setOnRefreshListener {
            viewModel.selectedMovie.value?.let {
                viewModel.refreshList(binding.root.context, it, TypeTabs.PLANETS)
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
            viewModel.syncList(binding.root.context, it, TypeTabs.PLANETS)
        }
        viewModel.planetsList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                binding.errorContainer.container.visibility = View.VISIBLE
            } else {
                binding.errorContainer.container.visibility = View.GONE
            }

            binding.progressBar.visibility = View.GONE
            (binding.planetsRecycler.adapter as PlanetAdapter).updateList(list)
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
