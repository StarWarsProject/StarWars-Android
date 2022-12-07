package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.MovieEntity

interface MovieLocalRepository {
    fun addLocalMovies(movieEntity: MovieEntity)
    fun getLocalMovies(): List<MovieEntity>
    fun getSingleMovie(movieId: Int): MovieEntity
}
