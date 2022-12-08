package com.example.starwarsapp.data.local.managers

import com.example.starwarsapp.data.local.db.StarWarsDB
import com.example.starwarsapp.data.local.interfaces.PlanetLocalRepository
import com.example.starwarsapp.data.local.models.MoviePlanetsEntity
import com.example.starwarsapp.data.local.models.PlanetEntity

class PlanetLocalManager(private val localStarWarsDB: StarWarsDB) : PlanetLocalRepository {
    override suspend fun getPlanetsForMovie(movieId: Int): List<PlanetEntity> {
        return localStarWarsDB.moviePlanetsDao().getPlanets(movieId)
    }

    override suspend fun storePlanetForMovie(planetEntity: PlanetEntity, movieId: Int) {
        localStarWarsDB.planetDao().addPlanet(planetEntity)
        localStarWarsDB.moviePlanetsDao().addMoviePlanet(MoviePlanetsEntity(movieId, planetEntity.id))
    }
}
