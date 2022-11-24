package com.example.starwarsapp.ui.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.starwarsapp.R
import com.example.starwarsapp.databinding.FragmentMoviesBinding
import com.example.starwarsapp.ui.movies.viewModel.MoviesViewModel
import com.example.starwarsapp.utils.ConnectionLiveData
import com.example.starwarsapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var cld: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNetworkConnection()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Utils.checkForInternet(binding.root.context)) {
            viewModel.getAllMovies()
        } else {
            viewModel.moviesList.observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setTitle(R.string.error_connection_title)
                    builder.setMessage(R.string.error_connection_message)
                    builder.setPositiveButton(R.string.ok) { _, _ ->
                    }
                    builder.show()
                }
            }
            viewModel.getAllMoviesLocally()
        }
    }

    // In case the connection is lost or the connection returns during using the app, this function will handle that and we will do anything if needed
    private fun checkNetworkConnection() {
        cld = ConnectionLiveData(requireActivity().application)

        cld.observe(this) { isConnected ->
            if (isConnected) {
                println("Internet Available")
            } else {
                println("Internet Not Available")
            }
        }
    }
}
