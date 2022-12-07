package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.CharacterEntity

interface CharacterLocalRepository {
    suspend fun getCharactersForMovie(movieId: Int): List<CharacterEntity>
    suspend fun storeCharacterForMovie(characterEntity: CharacterEntity, movieId: Int)
}
