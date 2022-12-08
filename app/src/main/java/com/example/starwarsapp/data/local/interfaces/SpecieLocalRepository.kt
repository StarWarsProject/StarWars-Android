package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.SpecieEntity

interface SpecieLocalRepository {
    suspend fun getSpeciesForMovie(movieId: Int): List<SpecieEntity>
    suspend fun storeSpecieForMovie(specieEntity: SpecieEntity, movieId: Int)
}
