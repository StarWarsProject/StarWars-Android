package com.example.starwarsapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey
    val id: Int,
    val name: String,
    val birth: String,
    val description: String,
    val gender: String,
    val height: String,
    val image: String,
    val planet: String,
    val specie: String,
    val createdAt: Long,
    val updatedAt: Long
)
