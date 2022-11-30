package com.example.starwarsapp.ui.movieDetail.characters.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.data.local.interfaces.MovieLocalRepository
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel
@Inject
constructor(private val swapiRepository: SwapiRepository, private val movieLocalRepository: MovieLocalRepository) : ViewModel() {
    private var _charactersList: MutableLiveData<List<CharacterEntity>> = MutableLiveData()
    val moviesList: LiveData<List<CharacterEntity>> = _charactersList

    fun getAllCharactersForMovie(charactersIds: List<String>) = viewModelScope.launch {
        val characters = swapiRepository.getCharactersForMovie(charactersIds)
//        if (characters.data != null) {
//            _charactersList = characters.data.map { it.toEntity() }
//        }
    }
}
