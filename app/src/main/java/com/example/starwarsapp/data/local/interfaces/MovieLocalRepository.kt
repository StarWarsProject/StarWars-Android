package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.data.local.models.PlanetEntity

interface MovieLocalRepository {
    fun addLocalMovies(movieEntity: MovieEntity)
    fun getLocalMovies(): List<MovieEntity>
    fun getCharactersForMovie(movieId: Int): List<CharacterEntity>
    fun storeCharacterForMovie(characterEntity: CharacterEntity, movieId: Int)
    fun getPlanetsForMovie(movieId: Int): List<PlanetEntity>
    fun storePlanetForMovie(planetEntity: PlanetEntity, movieId: Int)
}
