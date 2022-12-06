package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.PlanetEntity

interface PlanetLocalRepository {
    fun getPlanetsForMovie(movieId: Int): List<PlanetEntity>
    fun storePlanetForMovie(planetEntity: PlanetEntity, movieId: Int)
}
