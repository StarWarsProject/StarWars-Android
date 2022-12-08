package com.example.starwarsapp.data.local.managers

import com.example.starwarsapp.data.local.db.StarWarsDB
import com.example.starwarsapp.data.local.interfaces.SpecieLocalRepository
import com.example.starwarsapp.data.local.models.MovieSpeciesEntity
import com.example.starwarsapp.data.local.models.SpecieEntity
import com.example.starwarsapp.data.remote.models.Specie
import com.example.starwarsapp.utils.ListUtil

class SpecieLocalManager(private val localStarWarsDB: StarWarsDB) : SpecieLocalRepository {
    override suspend fun getSpeciesForMovie(movieId: Int): List<SpecieEntity> {
        return localStarWarsDB.movieSpeciesDao().getSpecies(movieId)
    }

    override suspend fun storeSpecieForMovie(specieData: Specie) {
        val specieEnt = specieData.toEntity()
        localStarWarsDB.specieDao().addSpecie(specieEnt)
        specieData.films.forEach {
            localStarWarsDB.movieSpeciesDao().addMovieSpecie(MovieSpeciesEntity(ListUtil.splitToGetIdFromUrl(it).toInt(), specieEnt.id))
        }
    }

    override suspend fun removeSpecieFromMovie(specieId: Int, movieId: Int) {
        localStarWarsDB.movieSpeciesDao().removeSpecieFromMovie(specieId, movieId)
    }
}
