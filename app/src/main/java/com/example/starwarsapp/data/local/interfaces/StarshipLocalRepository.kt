package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.StarshipEntity

interface StarshipLocalRepository {
    suspend fun getStarshipsForMovie(movieId: Int): List<StarshipEntity>
    suspend fun storeStarshipForMovie(starshipEntity: StarshipEntity, movieId: Int)
}
