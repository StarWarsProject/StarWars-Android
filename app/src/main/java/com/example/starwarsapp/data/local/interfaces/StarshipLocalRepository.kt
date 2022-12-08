package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.StarshipEntity
import com.example.starwarsapp.data.remote.models.Starship

interface StarshipLocalRepository {
    suspend fun getStarshipsForMovie(movieId: Int): List<StarshipEntity>
    suspend fun storeStarshipForMovie(starshipData: Starship)
    suspend fun removeStarshipFromMovie(starshipId: Int, movieId: Int)
}
