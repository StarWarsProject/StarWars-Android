package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.CharacterEntity

interface CharacterLocalRepository {
    fun getCharactersForMovie(movieId: Int): List<CharacterEntity>
    fun storeCharacterForMovie(characterEntity: CharacterEntity, movieId: Int)
}
