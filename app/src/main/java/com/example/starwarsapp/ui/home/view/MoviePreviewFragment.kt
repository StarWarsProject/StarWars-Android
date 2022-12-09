package com.example.starwarsapp.ui.home.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.starwarsapp.databinding.FragmentMoviePreviewBinding
import com.example.starwarsapp.ui.home.viewModel.MoviesListViewModel
import com.example.starwarsapp.utils.DrawableManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviePreviewFragment : Fragment() {
    private var _binding: FragmentMoviePreviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesListViewModel by activityViewModels()

    @Inject
    lateinit var drawableManager: DrawableManager

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
        binding.errorContainer.visibility = View.GONE
        viewModel.activeMovie.observe(viewLifecycleOwner) {
            binding.movieItem = it
            if (it == null) {
                binding.errorContainer.visibility = View.VISIBLE
                binding.headerContainer.visibility = View.GONE
                binding.previewContainer.visibility = View.GONE
            } else {
                binding.errorContainer.visibility = View.GONE
                binding.headerContainer.visibility = View.VISIBLE
                binding.previewContainer.visibility = View.VISIBLE
                val imageDrawable = drawableManager.getDrawable(it.title)
                binding.ivMoviePoster.setImageDrawable(imageDrawable)
            }
        }
        binding.btnGoDetails.setOnClickListener {
            val targetMovie = viewModel.activeMovie.value ?: return@setOnClickListener
            val direction = MainScreenFragmentDirections.actionMainScreenFragmentToMovieDetailFragment(targetMovie.id)
            Log.d("preview", targetMovie.id.toString())
            findNavController().navigate(direction)
        }
    }
}
