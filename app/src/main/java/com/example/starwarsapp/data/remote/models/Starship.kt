package com.example.starwarsapp.data.remote.models

import com.example.starwarsapp.data.local.models.StarshipEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import java.util.*

data class Starship(
    val MGLT: String,
    val cargo_capacity: String,
    val consumables: String,
    val cost_in_credits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    val hyperdrive_rating: String,
    val length: String,
    val manufacturer: String,
    val max_atmosphering_speed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val pilots: List<Any>,
    val starship_class: String,
    val url: String
) : IBaseRemoteData {
    override fun toEntity(): StarshipEntity {
        val splitUrl = url.split("/")
        val id = splitUrl[splitUrl.size - 2]
        return StarshipEntity(
            id.toInt(),
            name,
            model,
            manufacturer,
            length,
            max_atmosphering_speed,
            crew,
            passengers,
            cargo_capacity,
            starship_class,
            Date().time,
            Date().time
        )
    }
}
