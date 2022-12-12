package com.example.starwarsapp.utils

import com.example.starwarsapp.data.remote.models.Movie
import com.example.starwarsapp.data.remote.models.MovieResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonReader {
    companion object {
        fun read(): List<Movie>? {
            val fileContent = this::class.java.classLoader?.getResource("movies.json")?.readText()
            fileContent?.let {
                val objectConverted: MovieResponse = Gson().fromJson(it, object : TypeToken<MovieResponse>() {}.type)
                return objectConverted.results
            }
            return null
        }
    }
}
