package com.example.starwarsapp.ui.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.starwarsapp.databinding.FragmentMainScreenBinding
import com.example.starwarsapp.utils.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var cld: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNetworkConnection()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
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
