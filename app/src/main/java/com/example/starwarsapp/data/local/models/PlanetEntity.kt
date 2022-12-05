package com.example.starwarsapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlanetEntity(
    @PrimaryKey
    override val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val region: String,
    val system: String,
    val climate: String,
    val terrain: String,
    val population: String,
    val createdAt: Long,
    val updatedAt: Long
) : BaseEntity(id = id) {
    fun planetLocalToPlanetView(): PlanetInfoView {
        return PlanetInfoView(id, name, climate, terrain)
    }
}

data class PlanetInfoView(
    val id: Int,
    val name: String,
    val climate: String,
    val terrain: String
)
