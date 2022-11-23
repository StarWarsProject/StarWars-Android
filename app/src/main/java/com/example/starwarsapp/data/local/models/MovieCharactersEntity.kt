package com.example.starwarsapp.data.local.models

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "characterId"])
data class MovieCharactersEntity(
    val movieId: Int,
    val characterId: Int
)
