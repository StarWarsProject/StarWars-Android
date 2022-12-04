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
        binding.refreshContainer.setOnRefreshListener {
            viewModel.selectedMovie.value?.let {
                viewModel.refreshPlanetsList(binding.root.context, it)
            }
        }
        setObservers()
        viewModel.selectedMovie.value?.let {
            viewModel.syncPlanetsList(binding.root.context, it)
        }
    }

    private fun setObservers() {
        viewModel.planetsList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                binding.errorContainer.container.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.errorContainer.container.visibility = View.GONE
                (binding.planetsRecycler.adapter as PlanetAdapter).updateList(list)
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
