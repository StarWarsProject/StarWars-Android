package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.MovieEntity

interface MovieLocalRepository {
    suspend fun addLocalMovies(movieEntity: MovieEntity)
    suspend fun getLocalMovies(): List<MovieEntity>
    suspend fun getSingleMovie(movieId: Int): MovieEntity
}
