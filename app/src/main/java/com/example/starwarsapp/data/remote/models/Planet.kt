package com.example.starwarsapp.data.remote.models

import com.example.starwarsapp.data.local.models.PlanetEntity
import java.util.*

data class Planet(
    val name: String = "",
    val rotation_period: String = "",
    val orbital_period: String = "",
    val diameter: String = "",
    val climate: String = "",
    val gravity: String = "",
    val terrain: String = "",
    val surface_water: String = "",
    val population: String = "",
    val residents: List<String> = listOf(),
    val films: List<String> = listOf(),
    val created: String = "",
    val edited: String = "",
    val url: String = ""
) {
    fun toEntity(): PlanetEntity {
        val splitUrl = url.split("/")
        val id = splitUrl[splitUrl.size - 2]
        return PlanetEntity(id.toInt(), name, "", "", "", "", climate, terrain, population, Date().time, Date().time)
    }
}
