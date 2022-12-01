package com.example.starwarsapp.ui.movieDetail.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.local.interfaces.MovieDataRepository
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.local.models.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
@Inject
constructor(private val movieDataRepository: MovieDataRepository) : ViewModel() {

    val selectedMovie: MutableLiveData<MovieEntity> by lazy {
        MutableLiveData<MovieEntity>()
    }
    val dataError: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val charactersList: MutableLiveData<List<CharacterEntity>> by lazy {
        MutableLiveData<List<CharacterEntity>>()
    }

    fun setSelectedMovie(movie: MovieEntity) {
        selectedMovie.value = movie
    }

    fun getAllCharactersForMovie(context: Context, movie: MovieEntity) = viewModelScope.launch {
        val data = movieDataRepository.getCharactersForMovie(context, movie).data
        if (data != null) {
            charactersList.value = data
            saveCharactersLocally(data, movie.id)
        } else {
            dataError.value = true
        }
    }
    private fun saveCharactersLocally(charactersList: List<CharacterEntity>, movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        movieDataRepository.storeCharactersForMovie(charactersList, movieId)
    }
}
