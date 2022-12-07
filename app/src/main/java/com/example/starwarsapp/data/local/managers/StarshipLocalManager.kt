package com.example.starwarsapp.data.local.managers

import com.example.starwarsapp.data.local.db.StarWarsDB
import com.example.starwarsapp.data.local.interfaces.StarshipLocalRepository
import com.example.starwarsapp.data.local.models.MovieStarshipsEntity
import com.example.starwarsapp.data.local.models.StarshipEntity
import com.example.starwarsapp.data.remote.models.Starship
import com.example.starwarsapp.utils.ListUtil

class StarshipLocalManager(private val localStarWarsDB: StarWarsDB) : StarshipLocalRepository {
    override suspend fun getStarshipsForMovie(movieId: Int): List<StarshipEntity> {
        return localStarWarsDB.movieStarshipDao().getStarships(movieId)
    }

    override suspend fun storeStarshipForMovie(starshipData: Starship) {
        val starshipEnt = starshipData.toEntity()
        localStarWarsDB.starshipDao().addStarship(starshipEnt)
        starshipData.films.forEach {
            localStarWarsDB.movieStarshipDao().addMovieStarship(MovieStarshipsEntity(ListUtil.splitToGetIdFromUrl(it).toInt(), starshipEnt.id))
        }
    }
}
