package com.example.starwarsapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    override val id: Int,
    val title: String,
    val openingCrawl: String,
    val director: String,
    val producer: String,
    val releaseDate: String,
    val characters: String,
    val planets: String,
    val starships: String,
    val vehicles: String,
    val species: String,
    val createdAt: Long,
    val updatedAt: Long
) : java.io.Serializable, BaseEntity(id = id)
