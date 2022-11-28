package com.example.starwarsapp.ui.movieDetail.view

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import androidx.navigation.fragment.navArgs
import com.example.starwarsapp.databinding.FragmentMovieDetailBinding
import com.example.starwarsapp.ui.movieDetail.adapters.PageAdapter
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
        binding.movieEntity = args.movie

        val imageDrawable = drawableManager.getDrawable(args.movie.title)
        binding.movieBg.setImageDrawable(imageDrawable)
//        binding.scrollOpCrawl.post {
//            object : Runnable {
//                override fun run() {
//                    binding.scrollOpCrawl.smoothScrollTo(0, binding.scrollOpCrawl.bottom)
//                }
//            }
//        }

        binding.scrollOpCrawl.viewTreeObserver.addOnGlobalLayoutListener {
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    binding.scrollOpCrawl.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    val objectAnimator = ObjectAnimator.ofInt(
                        binding.scrollOpCrawl,
                        "scrollY",
                        binding.scrollOpCrawl.getChildAt(0).height - binding.scrollOpCrawl.height
                    )
                    objectAnimator.duration = 10000
                    objectAnimator.interpolator = LinearInterpolator()
                    objectAnimator.start()
                }
            }
        }

        binding.fragmentsContainer.adapter =
            activity?.let { PageAdapter(it.supportFragmentManager) }
        binding.tabs.setupWithViewPager(binding.fragmentsContainer)
    }
}
