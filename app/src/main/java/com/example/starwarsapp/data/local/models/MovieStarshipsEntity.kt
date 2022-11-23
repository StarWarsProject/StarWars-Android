package com.example.starwarsapp.data.local.models

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "starshipId"])
data class MovieStarshipsEntity(
    val movieId: Int,
    val starshipId: Int
)
