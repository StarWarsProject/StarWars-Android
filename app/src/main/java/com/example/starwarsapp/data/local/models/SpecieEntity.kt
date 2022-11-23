package com.example.starwarsapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SpecieEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val language: String,
    val planet: String,
    val classification: String,
    val createdAt: Long,
    val updatedAt: Long
)
