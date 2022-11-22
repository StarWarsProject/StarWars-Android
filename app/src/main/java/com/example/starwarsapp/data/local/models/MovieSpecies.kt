package com.example.starwarsapp.data.local.models

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "planetId"])
data class MovieSpecies(
    val movieId: Int,
    val specieId: Int
)
