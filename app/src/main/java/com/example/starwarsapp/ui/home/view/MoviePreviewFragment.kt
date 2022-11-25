package com.example.starwarsapp.ui.home.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.starwarsapp.databinding.FragmentMoviePreviewBinding
import com.example.starwarsapp.ui.home.viewModel.MoviesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviePreviewFragment : Fragment() {
    private var _binding: FragmentMoviePreviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviePreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.activeMovie.observe(viewLifecycleOwner) {
            binding.movieItem = it
            val uri = "@drawable/${it.title.lowercase().split(" ").joinToString("_")}"
            val imageResource = resources.getIdentifier(uri, null, requireContext().packageName)
            val imageDrawable = requireContext().getDrawable(imageResource)
            binding.ivMoviePoster.setImageDrawable(imageDrawable)
        }
    }
}
