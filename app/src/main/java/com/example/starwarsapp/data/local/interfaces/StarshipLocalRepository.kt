package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.StarshipEntity

interface StarshipLocalRepository {
    fun getStarshipsForMovie(movieId: Int): List<StarshipEntity>
    fun storeStarshipForMovie(starshipEntity: StarshipEntity, movieId: Int)
}
