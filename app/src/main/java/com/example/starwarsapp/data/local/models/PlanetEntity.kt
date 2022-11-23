package com.example.starwarsapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlanetEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val region: String,
    val system: String,
    val createdAt: Long,
    val updatedAt: Long
)
