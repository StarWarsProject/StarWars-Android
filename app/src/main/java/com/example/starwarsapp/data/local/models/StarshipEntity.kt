package com.example.starwarsapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StarshipEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val model: String,
    val manufacturer: String,
    val length: String,
    val maxAtmospheringSpeed: String,
    val crew: String,
    val passengers: String,
    val cargoCapacity: String,
    val starshipClass: String,
    val createdAt: Long,
    val updatedAt: Long
)
