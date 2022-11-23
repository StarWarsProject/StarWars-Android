package com.example.starwarsapp.data.local.models

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "planetId"])
data class MoviePlanetsEntity(
    val movieId: Int,
    val planetId: Int
)
