package com.example.starwarsapp.data.local.managers

import com.example.starwarsapp.data.local.db.StarWarsDB
import com.example.starwarsapp.data.local.interfaces.StarshipLocalRepository
import com.example.starwarsapp.data.local.models.MovieStarshipsEntity
import com.example.starwarsapp.data.local.models.StarshipEntity

class StarshipLocalManager(private val localStarWarsDB: StarWarsDB) : StarshipLocalRepository {
    override suspend fun getStarshipsForMovie(movieId: Int): List<StarshipEntity> {
        return localStarWarsDB.movieStarshipDao().getStarships(movieId)
    }

    override suspend fun storeStarshipForMovie(starshipEntity: StarshipEntity, movieId: Int) {
        localStarWarsDB.starshipDao().addStarship(starshipEntity)
        localStarWarsDB.movieStarshipDao().addMovieStarship(MovieStarshipsEntity(movieId, starshipEntity.id))
    }
}
