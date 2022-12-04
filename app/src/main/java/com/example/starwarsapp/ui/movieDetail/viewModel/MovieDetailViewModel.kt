package com.example.starwarsapp.ui.movieDetail.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.local.interfaces.CharacterDataRepository
import com.example.starwarsapp.data.local.interfaces.PlanetDataRepository
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.data.local.models.PlanetEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
@Inject
constructor(private val characterDataRepository: CharacterDataRepository, private val planetDataRepository: PlanetDataRepository) : ViewModel() {

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
    val planetsList: MutableLiveData<List<PlanetEntity>> by lazy {
        MutableLiveData<List<PlanetEntity>>()
    }

    fun setSelectedMovie(movie: MovieEntity) {
        selectedMovie.value = movie
    }

    fun refreshCharactersList(context: Context, movie: MovieEntity) = viewModelScope.launch {
        val data = characterDataRepository.getCharactersForMovieFromInternet(context, movie.characters).data
        if (data != null) {
            charactersList.value = data
            syncError.value = false
            saveCharactersLocally(data, movie.id)
        } else {
            dataError.value = true
        }
    }

    private fun saveCharactersLocally(charactersList: List<CharacterEntity>, movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        characterDataRepository.storeCharactersForMovie(charactersList, movieId)
    }

    fun syncCharactersList(context: Context, movie: MovieEntity) = viewModelScope.launch {
        val response = characterDataRepository.syncCharactersData(context, movie)
        if (response.data != null) {
            charactersList.value = response.data
        }
        syncError.value = response.message != null
    }

    fun refreshPlanetsList(context: Context, movie: MovieEntity) = viewModelScope.launch {
        val data = planetDataRepository.getPlanetsForMovieFromInternet(context, movie.planets).data
        if (data != null) {
            planetsList.value = data
            syncError.value = false
            savePlanetsLocally(data, movie.id)
        } else {
            dataError.value = true
        }
    }

    private fun savePlanetsLocally(planetsList: List<PlanetEntity>, movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        planetDataRepository.storePlanetsForMovie(planetsList, movieId)
    }

    fun syncPlanetsList(context: Context, movie: MovieEntity) = viewModelScope.launch {
        val response = planetDataRepository.syncCPlanetsData(context, movie)
        if (response.data != null) {
            planetsList.value = response.data
        }
        syncError.value = response.message != null
    }
}
