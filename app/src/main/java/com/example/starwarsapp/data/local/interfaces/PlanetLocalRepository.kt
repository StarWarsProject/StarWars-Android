package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.PlanetEntity
import com.example.starwarsapp.data.remote.models.Planet

interface PlanetLocalRepository {
    suspend fun getPlanetsForMovie(movieId: Int): List<PlanetEntity>
    suspend fun storePlanetForMovie(planetData: Planet)
    suspend fun removePlanetFromMovie(planetId: Int, movieId: Int)
}
