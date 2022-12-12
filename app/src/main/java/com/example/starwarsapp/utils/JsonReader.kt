package com.example.starwarsapp.utils

import com.example.starwarsapp.data.remote.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonReader {
    companion object {
        fun readMovies(): List<Movie>? {
            val fileContent = this::class.java.classLoader?.getResource("movies.json")?.readText()
            fileContent?.let {
                val objectConverted: MovieResponse = Gson().fromJson(it, object : TypeToken<MovieResponse>() {}.type)
                return objectConverted.results
            }
            return null
        }
        fun readCharacter(): List<People>? {
            val fileContent = this::class.java.classLoader?.getResource("people.json")?.readText()
            fileContent?.let {
                val objectConverted: People = Gson().fromJson(it, object : TypeToken<People>() {}.type)
                return listOf(objectConverted)
            }
            return null
        }
        fun readPlanet(): List<Planet>? {
            val fileContent = this::class.java.classLoader?.getResource("planet.json")?.readText()
            fileContent?.let {
                val objectConverted: Planet = Gson().fromJson(it, object : TypeToken<Planet>() {}.type)
                return listOf(objectConverted)
            }
            return null
        }
        fun readSpecie(): List<Specie>? {
            val fileContent = this::class.java.classLoader?.getResource("specie.json")?.readText()
            fileContent?.let {
                val objectConverted: Specie = Gson().fromJson(it, object : TypeToken<Specie>() {}.type)
                return listOf(objectConverted)
            }
            return null
        }
        fun readStarShip(): List<Starship>? {
            val fileContent = this::class.java.classLoader?.getResource("starships.json")?.readText()
            fileContent?.let {
                val objectConverted: Starship = Gson().fromJson(it, object : TypeToken<Starship>() {}.type)
                return listOf(objectConverted)
            }
            return null
        }
    }
}
