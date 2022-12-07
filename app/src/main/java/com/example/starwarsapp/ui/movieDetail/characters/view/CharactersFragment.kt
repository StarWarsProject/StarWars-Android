package com.example.starwarsapp.ui.movieDetail.characters.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsapp.databinding.FragmentCharactersBinding
import com.example.starwarsapp.ui.movieDetail.adapters.CharacterAdapter
import com.example.starwarsapp.ui.movieDetail.viewModel.MovieDetailViewModel
import com.example.starwarsapp.ui.movieDetail.viewModel.TypeTabs

class CharactersFragment : Fragment() {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(layoutInflater, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.charactersRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.charactersRecycler.adapter = CharacterAdapter(listOf())
        binding.tvNotSynced.visibility = View.GONE
        binding.refreshContainer.setOnRefreshListener {
            viewModel.selectedMovie.value?.let {
                viewModel.refreshList(binding.root.context, it, TypeTabs.CHARACTERS)
            }
        }
        setObservers()
        viewModel.selectedMovie.value?.let {
            viewModel.syncList(binding.root.context, it, TypeTabs.CHARACTERS)
        }
    }

    private fun setObservers() {
        viewModel.charactersList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                binding.errorContainer.container.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.errorContainer.container.visibility = View.GONE
                (binding.charactersRecycler.adapter as CharacterAdapter).updateList(list)
            }
            binding.refreshContainer.isRefreshing = false
        }
        viewModel.dataError.observe(viewLifecycleOwner) { hasError ->
            binding.progressBar.visibility = View.GONE
            if (hasError) {
                binding.errorContainer.container.visibility = View.VISIBLE
            } else {
                binding.errorContainer.container.visibility = View.GONE
            }
            binding.refreshContainer.isRefreshing = false
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
