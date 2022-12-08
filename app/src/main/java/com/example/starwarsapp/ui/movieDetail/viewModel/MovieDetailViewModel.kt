package com.example.starwarsapp.ui.movieDetail.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.local.models.*
import com.example.starwarsapp.data.sync.interfaces.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class TypeTabs {
    CHARACTERS, PLANETS, SPECIES, SHIPS, VEHICLES
}

@HiltViewModel
class MovieDetailViewModel
@Inject
constructor(
    private val characterDataRepository: CharacterDataRepository,
    private val movieDataRepository: MovieDataRepository,
    private val planetDataRepository: PlanetDataRepository,
    private val specieDataRepository: SpecieDataRepository,
    private val starshipDataRepository: StarshipDataRepository
) : ViewModel() {

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
    val speciesList: MutableLiveData<List<SpecieEntity>> by lazy {
        MutableLiveData<List<SpecieEntity>>()
    }
    val starshipsList: MutableLiveData<List<StarshipEntity>> by lazy {
        MutableLiveData<List<StarshipEntity>>()
    }

    fun setSelectedMovie(movieId: Int) = viewModelScope.launch {
        val result = movieDataRepository.getSingleMovie(movieId)
        selectedMovie.value = result
    }

    fun refreshList(context: Context, movie: MovieEntity, type: TypeTabs) = viewModelScope.launch(Dispatchers.IO) {
        var data: List<BaseEntity>? = null
        when (type) {
            TypeTabs.CHARACTERS -> {
                data = characterDataRepository.getDataFromInternet(context, movie.characters).data
                if (data != null) {
                    withContext(Dispatchers.Main) {
                        charactersList.value = data as List<CharacterEntity>
                    }
                }
            }
            TypeTabs.PLANETS -> {
                data = planetDataRepository.getDataFromInternet(context, movie.planets).data
                if (data != null) {
                    withContext(Dispatchers.Main) {
                        planetsList.value = data as List<PlanetEntity>
                    }
                }
            }
            TypeTabs.SPECIES -> {
                data = specieDataRepository.getDataFromInternet(context, movie.species).data
                if (data != null) {
                    withContext(Dispatchers.Main) {
                        speciesList.value = data as List<SpecieEntity>
                    }
                }
            }
            TypeTabs.SHIPS -> {
                data = starshipDataRepository.getDataFromInternet(context, movie.starships).data
                if (data != null) {
                    withContext(Dispatchers.Main) {
                        starshipsList.value = data as List<StarshipEntity>
                    }
                }
            }
            else -> {}
        }
        if (data != null) {
            withContext(Dispatchers.Main) {
                syncError.value = false
            }
            saveDataLocally(data, movie.id, type)
        } else {
            dataError.value = true
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun saveDataLocally(dataList: List<BaseEntity>, movieId: Int, type: TypeTabs) = viewModelScope.launch {
        when (type) {
            TypeTabs.CHARACTERS -> characterDataRepository.storeData(dataList as List<CharacterEntity>, movieId)
            TypeTabs.PLANETS -> planetDataRepository.storeData(dataList as List<PlanetEntity>, movieId)
            TypeTabs.SPECIES -> specieDataRepository.storeData(dataList as List<SpecieEntity>, movieId)
            else -> {}
        }
    }

    fun syncList(context: Context, movie: MovieEntity, type: TypeTabs) = viewModelScope.launch {
        var response: List<BaseEntity>?
        var messageError: String? = null
        when (type) {
            TypeTabs.CHARACTERS -> {
                val result = characterDataRepository.syncData(context, movie.characters, "movie", movie.id)
                response = result.data
                messageError = result.message
                if (response != null) {
                    charactersList.value = response
                }
            }
            TypeTabs.PLANETS -> {
                val result = planetDataRepository.syncData(context, movie.planets, "movie", movie.id)
                response = result.data
                messageError = result.message
                if (response != null) {
                    planetsList.value = response
                }
            }
            TypeTabs.SPECIES -> {
                val result = specieDataRepository.syncData(context, movie.species, "movie", movie.id)
                response = result.data
                messageError = result.message
                if (response != null) {
                    speciesList.value = response
                }
            }
            TypeTabs.SHIPS -> {
                val result = starshipDataRepository.syncData(context, movie.starships, "movie", movie.id)
                response = result.data
                messageError = result.message
                if (response != null) {
                    starshipsList.value = response
                }
            }
            else -> {}
        }
        syncError.value = messageError != null
    }
}
