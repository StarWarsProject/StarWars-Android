package com.example.starwarsapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val openingCrawl: String,
    val director: String,
    val producer: String,
    val releaseDate: String,
    val createdAt: Long,
    val updatedAt: Long
) : java.io.Serializable
