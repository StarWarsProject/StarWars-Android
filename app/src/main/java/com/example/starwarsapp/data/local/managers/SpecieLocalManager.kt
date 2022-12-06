package com.example.starwarsapp.data.local.managers

import com.example.starwarsapp.data.local.db.StarWarsDB
import com.example.starwarsapp.data.local.interfaces.SpecieLocalRepository
import com.example.starwarsapp.data.local.models.MovieSpeciesEntity
import com.example.starwarsapp.data.local.models.SpecieEntity

class SpecieLocalManager(private val localStarWarsDB: StarWarsDB) : SpecieLocalRepository {
    override fun getSpeciesForMovie(movieId: Int): List<SpecieEntity> {
        return localStarWarsDB.movieSpeciesDao().getSpecies(movieId)
    }

    override fun storeSpecieForMovie(specieEntity: SpecieEntity, movieId: Int) {
        localStarWarsDB.specieDao().addSpecie(specieEntity)
        localStarWarsDB.movieSpeciesDao().addMovieSpecie(MovieSpeciesEntity(movieId, specieEntity.id))
    }
}
