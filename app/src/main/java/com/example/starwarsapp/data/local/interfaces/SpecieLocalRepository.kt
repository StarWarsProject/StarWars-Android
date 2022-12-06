package com.example.starwarsapp.data.local.interfaces

import com.example.starwarsapp.data.local.models.SpecieEntity

interface SpecieLocalRepository {
    fun getSpeciesForMovie(movieId: Int): List<SpecieEntity>
    fun storeSpecieForMovie(specieEntity: SpecieEntity, movieId: Int)
}
