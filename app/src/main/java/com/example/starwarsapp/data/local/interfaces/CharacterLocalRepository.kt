package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.remote.models.People

interface CharacterLocalRepository {
    suspend fun getCharactersForMovie(movieId: Int): List<CharacterEntity>
    suspend fun storeCharacterForMovie(characterData: People)
    suspend fun removeCharacterFromMovie(charId: Int, movieId: Int)
}
