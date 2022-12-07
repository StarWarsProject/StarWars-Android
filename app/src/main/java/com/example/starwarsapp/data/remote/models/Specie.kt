package com.example.starwarsapp.data.remote.models

import com.example.starwarsapp.data.local.models.SpecieEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import java.util.*

data class Specie(
    val average_height: String = "",
    val average_lifespan: String = "",
    val classification: String = "",
    val created: String = "",
    val designation: String = "",
    val edited: String = "",
    val eye_colors: String = "",
    val films: List<String> = listOf(),
    val hair_colors: String = "",
    val homeworld: String = "",
    val language: String = "",
    val name: String = "",
    val people: List<String> = listOf(),
    val skin_colors: String = "",
    val url: String = ""
) : IBaseRemoteData {
    override fun toEntity(): SpecieEntity {
        val splitUrl = url.split("/")
        val id = splitUrl[splitUrl.size - 2]
        return SpecieEntity(
            id.toInt(),
            name,
            "",
            "",
            language,
            "",
            classification,
            Date().time,
            Date().time
        )
    }
}
