package com.example.starwarsapp.ui.movies.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.local.interfaces.MovieLocalRepository
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.remote.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel
@Inject
constructor(private val swapiRepository: SwapiRepository, private val movieLocalRepository: MovieLocalRepository) : ViewModel() {

    var _moviesList: MutableLiveData<List<MovieEntity>> = MutableLiveData()
    val moviesList: LiveData<List<MovieEntity>> = _moviesList

    fun getAllMovies() = viewModelScope.launch {
        val moviesResponse = swapiRepository.getAllMovies()
        if (moviesResponse.data != null) {
            saveMoviesLocally(moviesResponse.data)
        }
    }

    fun getAllMoviesLocally() = viewModelScope.launch {
        _moviesList.value = movieLocalRepository.getLocalMovies()
    }

    fun saveMoviesLocally(movies: List<Movie>) = viewModelScope.launch {
        for (item in movies) {
            val movie = MovieEntity(item.episode_id, item.title, item.opening_crawl, item.director, item.producer, item.release_date, Date().time, Date().time)
            movieLocalRepository.addLocalMovies(movie)
        }
    }
}
