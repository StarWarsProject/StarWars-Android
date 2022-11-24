package com.example.starwarsapp.data.local.managers

import com.example.starwarsapp.data.local.db.StarWarsDB
import com.example.starwarsapp.data.local.interfaces.MovieLocalRepository
import com.example.starwarsapp.data.local.models.MovieEntity

class MovieLocalManager(private val localStarWarsDB: StarWarsDB) : MovieLocalRepository {
    override fun addLocalMovies(movieEntity: MovieEntity) {
        localStarWarsDB.movieDao().addMovie(movieEntity)
    }

    override fun getLocalMovies(): List<MovieEntity> {
        return localStarWarsDB.movieDao().getAllMovies()
    }
}
