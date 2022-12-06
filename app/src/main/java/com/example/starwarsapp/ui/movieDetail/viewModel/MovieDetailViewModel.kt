package com.example.starwarsapp.ui.movieDetail.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.sync.interfaces.CharacterDataRepository
import com.example.starwarsapp.data.sync.interfaces.MovieDataRepository
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.local.models.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
@Inject
constructor(private val characterDataRepository: CharacterDataRepository, private val movieDataRepository: MovieDataRepository) : ViewModel() {

    val selectedMovie: MutableLiveData<MovieEntity> by lazy {
        MutableLiveData<MovieEntity>()
    }
    val dataError: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val syncError: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val charactersList: MutableLiveData<List<CharacterEntity>> by lazy {
        MutableLiveData<List<CharacterEntity>>()
    }

    fun setSelectedMovie(movieId: Int) = viewModelScope.launch {
        selectedMovie.value = movieDataRepository.getSingleMovie(movieId)
    }

    fun refreshCharactersList(context: Context, movie: MovieEntity) = viewModelScope.launch {
        val data = characterDataRepository.getDataFromInternet(context, movie.characters).data
        if (data != null) {
            charactersList.value = data
            syncError.value = false
            saveCharactersLocally(data, movie.id)
        } else {
            dataError.value = true
        }
    }

    private fun saveCharactersLocally(charactersList: List<CharacterEntity>, movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        characterDataRepository.storeData(charactersList, movieId)
    }

    fun syncCharactersList(context: Context, movie: MovieEntity) = viewModelScope.launch {
        val response = characterDataRepository.syncData(context, movie.characters, "movie", movie.id)
        if (response.data != null) {
            charactersList.value = response.data
        }
        syncError.value = response.message != null
    }
}
