package com.example.starwarsapp.data.local.managers

import com.example.starwarsapp.data.local.db.StarWarsDB
import com.example.starwarsapp.data.local.interfaces.PlanetLocalRepository
import com.example.starwarsapp.data.local.models.MoviePlanetsEntity
import com.example.starwarsapp.data.local.models.PlanetEntity
import com.example.starwarsapp.data.remote.models.Planet
import com.example.starwarsapp.utils.ListUtil

class PlanetLocalManager(private val localStarWarsDB: StarWarsDB) : PlanetLocalRepository {
    override suspend fun getPlanetsForMovie(movieId: Int): List<PlanetEntity> {
        return localStarWarsDB.moviePlanetsDao().getPlanets(movieId)
    }

    override suspend fun storePlanetForMovie(planetData: Planet) {
        val planetEnt = planetData.toEntity()
        localStarWarsDB.planetDao().addPlanet(planetEnt)
        planetData.films.forEach {
            localStarWarsDB.moviePlanetsDao().addMoviePlanet(MoviePlanetsEntity(ListUtil.splitToGetIdFromUrl(it).toInt(), planetEnt.id))
        }
    }

    override suspend fun removePlanetFromMovie(planetId: Int, movieId: Int) {
        localStarWarsDB.moviePlanetsDao().removePlanetFromMovie(planetId, movieId)
    }
}
