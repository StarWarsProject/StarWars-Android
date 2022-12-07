package com.example.starwarsapp.ui.movieDetail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.starwarsapp.databinding.FragmentMovieDetailBinding
import com.example.starwarsapp.ui.movieDetail.adapters.PageAdapter
import com.example.starwarsapp.ui.movieDetail.viewModel.MovieDetailViewModel
import com.example.starwarsapp.utils.DrawableManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val args: MovieDetailFragmentArgs by navArgs()

    @Inject
    lateinit var drawableManager: DrawableManager
    private val viewModel: MovieDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setSelectedMovie(args.movieId)

        viewModel.selectedMovie.observe(viewLifecycleOwner) {
            binding.movieEntity = it
            val imageDrawable = drawableManager.getDrawable(it.title)
            binding.movieBg.setImageDrawable(imageDrawable)
        }

        binding.fragmentsContainer.adapter = PageAdapter(childFragmentManager)
        binding.tabs.setupWithViewPager(binding.fragmentsContainer)
    }
}
