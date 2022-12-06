package com.example.starwarsapp.data.remote.models

import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import java.util.Date

data class People(
    val birth_year: String = "",
    val eye_color: String = "",
    val films: List<String> = listOf(),
    val gender: String = "",
    val hair_color: String = "",
    val height: String = "",
    val homeworld: String = "",
    val mass: String = "",
    val name: String = "",
    val skin_color: String = "",
    val created: String = "",
    val edited: String = "",
    val species: List<String> = listOf(),
    val starships: List<String> = listOf(),
    val url: String = "",
    val vehicles: List<String> = listOf()
) : IBaseRemoteData {
    override fun toEntity(): CharacterEntity {
        val splitUrl = url.split("/")
        val id = splitUrl[splitUrl.size - 2]
        return CharacterEntity(id.toInt(), name, birth_year, "", gender, height, "", homeworld, "", Date().time, Date().time)
    }
}
