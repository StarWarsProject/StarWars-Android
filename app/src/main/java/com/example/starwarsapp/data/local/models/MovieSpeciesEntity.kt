package com.example.starwarsapp.data.local.models

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "specieId"])
data class MovieSpeciesEntity(
    val movieId: Int,
    val specieId: Int
)
