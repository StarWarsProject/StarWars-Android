package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.PlanetEntity

interface PlanetLocalRepository {
    suspend fun getPlanetsForMovie(movieId: Int): List<PlanetEntity>
    suspend fun storePlanetForMovie(planetEntity: PlanetEntity, movieId: Int)
}
