package com.example.starwarsapp.ui.home.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
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

    var _moviesList: MutableLiveData<List<MovieEntity>> = MutableLiveData()
    val moviesList: LiveData<List<MovieEntity>> = _moviesList
    val activeMovie: MutableLiveData<MovieEntity?> = MutableLiveData()

    fun getAllMovies(context: Context) = viewModelScope.launch {
        val moviesResponse = repository.getAllMovies(context)
        print("getting")
        val data = moviesResponse.data
        if (data != null) {
            print("is not null")
            _moviesList.value = data
            activeMovie.value = data.first()
            saveMoviesLocally(data)
        } else {
            print("is null")
        }
    }

    private fun saveMoviesLocally(movies: List<MovieEntity>) = viewModelScope.launch(Dispatchers.IO) {
        repository.storeAllMovies(movies)
    }

    fun filterByReleaseDate() {
        val newList = mutableListOf<MovieEntity>()
        newList.addAll(_moviesList.value ?: listOf())
        newList.sortBy { it.releaseDate }
        _moviesList.value = newList
    }

    fun filterByHistory() {
        val newList = mutableListOf<MovieEntity>()
        newList.addAll(_moviesList.value ?: listOf())
        newList.sortBy { it.id }
        _moviesList.value = newList
    }

    fun updateActiveMovie(movie: MovieEntity) {
        activeMovie.value = movie
    }
}
