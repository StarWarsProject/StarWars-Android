package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.SpecieEntity
import com.example.starwarsapp.data.remote.models.Specie

interface SpecieLocalRepository {
    suspend fun getSpeciesForMovie(movieId: Int): List<SpecieEntity>
    suspend fun storeSpecieForMovie(specieData: Specie)
    suspend fun removeSpecieFromMovie(specieId: Int, movieId: Int)
}
