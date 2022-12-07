package com.example.starwarsapp.ui.home.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.sync.interfaces.MovieDataRepository
import com.example.starwarsapp.data.local.models.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel
@Inject
constructor(private val repository: MovieDataRepository) : ViewModel() {

    companion object {
        val LOGIDENTIFIER = "MoviesListViewModel"
    }
    val moviesList: MutableLiveData<List<MovieEntity>> by lazy {
        MutableLiveData<List<MovieEntity>>()
    }
    val activeMovie: MutableLiveData<MovieEntity?> by lazy {
        MutableLiveData<MovieEntity?>()
    }

    fun getAllMovies(context: Context) = viewModelScope.launch {
        val moviesResponse = repository.getAllMovies(context)
        val data = moviesResponse.data
        if (data != null) {
            moviesList.value = data
            activeMovie.value = data.first()
            saveMoviesLocally(data)
        } else {
            moviesList.value = listOf()
            activeMovie.value = null
            Log.d(LOGIDENTIFIER, "data is null")
        }
    }

    private fun saveMoviesLocally(movies: List<MovieEntity>) = viewModelScope.launch(Dispatchers.IO) {
        repository.storeAllMovies(movies)
    }

    fun filterByReleaseDate() {
        val newList = mutableListOf<MovieEntity>()
        newList.addAll(moviesList.value ?: listOf())
        newList.sortBy { it.releaseDate }
        moviesList.value = newList
    }

    fun filterByHistory() {
        val newList = mutableListOf<MovieEntity>()
        newList.addAll(moviesList.value ?: listOf())
        newList.sortBy { it.episode_id }
        moviesList.value = newList
    }

    fun updateActiveMovie(movie: MovieEntity) {
        activeMovie.value = movie
    }
}
