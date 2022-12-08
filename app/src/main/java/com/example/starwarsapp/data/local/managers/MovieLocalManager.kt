package com.example.starwarsapp.data.local.managers

import com.example.starwarsapp.data.local.db.StarWarsDB
import com.example.starwarsapp.data.local.interfaces.MovieLocalRepository
import com.example.starwarsapp.data.local.models.MovieEntity

class MovieLocalManager(private val localStarWarsDB: StarWarsDB) : MovieLocalRepository {
    override suspend fun addLocalMovies(movieEntity: MovieEntity) {
        localStarWarsDB.movieDao().addMovie(movieEntity)
    }

    override suspend fun getLocalMovies(): List<MovieEntity> {
        return localStarWarsDB.movieDao().getAllMovies()
    }

    override suspend fun getSingleMovie(movieId: Int): MovieEntity {
        return localStarWarsDB.movieDao().getSingleMovie(movieId)
    }
}
