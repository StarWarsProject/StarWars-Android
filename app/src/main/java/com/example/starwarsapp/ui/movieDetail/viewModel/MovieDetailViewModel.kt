package com.example.starwarsapp.ui.movieDetail.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.local.models.*
import com.example.starwarsapp.data.sync.interfaces.*
import com.example.starwarsapp.di.IoDispatcher
import com.example.starwarsapp.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class TypeTabs {
    CHARACTERS, PLANETS, SPECIES, SHIPS
}

@HiltViewModel
class MovieDetailViewModel
@Inject
constructor(
    private val characterDataRepository: CharacterDataRepository,
    private val movieDataRepository: MovieDataRepository,
    private val planetDataRepository: PlanetDataRepository,
    private val specieDataRepository: SpecieDataRepository,
    private val starshipDataRepository: StarshipDataRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
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

    fun refreshList(context: Context, movie: MovieEntity, type: TypeTabs) =
        viewModelScope.launch(ioDispatcher) {
            var data: List<BaseEntity>? = null
            when (type) {
                TypeTabs.CHARACTERS -> {
                    data =
                        characterDataRepository.refreshData(context, movie.characters).data
                    data?.let {
                        withContext(mainDispatcher) {
                            charactersList.value = it
                        }
                    }
                }
                TypeTabs.PLANETS -> {
                    data = planetDataRepository.refreshData(context, movie.planets).data
                    data?.let {
                        withContext(mainDispatcher) {
                            planetsList.value = it
                        }
                    }
                }
                TypeTabs.SPECIES -> {
                    data = specieDataRepository.refreshData(context, movie.species).data
                    data?.let {
                        withContext(mainDispatcher) {
                            speciesList.value = it
                        }
                    }
                }
                TypeTabs.SHIPS -> {
                    data = starshipDataRepository.refreshData(context, movie.starships).data
                    data?.let {
                        withContext(mainDispatcher) {
                            starshipsList.value = it
                        }
                    }
                }
            }
            withContext(mainDispatcher) {
                data?.let {
                    syncError.value = false
                    syncList(context, movie, type)
                } ?: run {
                    dataError.value = true
                }
            }
        }

    fun syncList(context: Context, movie: MovieEntity, type: TypeTabs) = viewModelScope.launch {
        var messageError: String? = null
        when (type) {
            TypeTabs.CHARACTERS -> {
                val result =
                    characterDataRepository.syncData(context, movie.characters, "movie", movie.id)
                result.data?.let {
                    charactersList.value = it
                }
                messageError = result.message
            }
            TypeTabs.PLANETS -> {
                val result =
                    planetDataRepository.syncData(context, movie.planets, "movie", movie.id)
                result.data?.let {
                    planetsList.value = it
                }
                messageError = result.message
            }
            TypeTabs.SPECIES -> {
                val result =
                    specieDataRepository.syncData(context, movie.species, "movie", movie.id)
                result.data?.let {
                    speciesList.value = it
                }
                messageError = result.message
            }
            TypeTabs.SHIPS -> {
                val result =
                    starshipDataRepository.syncData(context, movie.starships, "movie", movie.id)
                result.data?.let {
                    starshipsList.value = it
                }
                messageError = result.message
            }
        }
        syncError.value = messageError != null
    }
}
