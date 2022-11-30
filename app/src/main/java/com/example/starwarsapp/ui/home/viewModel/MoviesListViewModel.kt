package com.example.starwarsapp.ui.home.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.local.interfaces.MovieDataRepository
import com.example.starwarsapp.data.local.models.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel
@Inject
constructor(private val repository: MovieDataRepository) : ViewModel() {

    val moviesList: MutableLiveData<List<MovieEntity>> by lazy {
        MutableLiveData<List<MovieEntity>>(listOf())
    }
    val activeMovie: MutableLiveData<MovieEntity?> = MutableLiveData()

    fun getAllMovies(context: Context) = viewModelScope.launch {
        val moviesResponse = repository.getAllMovies(context)
        val data = moviesResponse.data
        if (data != null) {
            moviesList.value = data
            activeMovie.value = data.first()
            saveMoviesLocally(data)
        }
    }

    private fun saveMoviesLocally(movies: List<MovieEntity>) = viewModelScope.launch(Dispatchers.IO) {
        repository.storeAllMovies(movies)
    }

    fun updateActiveMovie(movie: MovieEntity) {
        activeMovie.value = movie
    }
}
