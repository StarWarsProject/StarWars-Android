package com.example.starwarsapp.data.remote.models

import com.example.starwarsapp.data.local.models.CharacterEntity
import java.util.Date

data class People(
    val birthYear: String = "",
    val eyeColor: String = "",
    val films: List<String> = listOf(),
    val gender: String = "",
    val hairColor: String = "",
    val height: String = "",
    val homeworld: String = "",
    val mass: String = "",
    val name: String = "",
    val skinColor: String = "",
    val created: String = "",
    val edited: String = "",
    val species: List<String> = listOf(),
    val starships: List<String> = listOf(),
    val url: String = "",
    val vehicles: List<String> = listOf()
) {
    fun toEntity(id: Int): CharacterEntity {
        return CharacterEntity(id, name, birthYear, "", gender, height, "", homeworld, "", Date().time, Date().time)
    }
}
