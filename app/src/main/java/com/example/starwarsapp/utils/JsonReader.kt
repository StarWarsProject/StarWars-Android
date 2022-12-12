package com.example.starwarsapp.utils

import android.content.Context
import com.example.starwarsapp.R
import com.example.starwarsapp.data.remote.models.Movie
import com.example.starwarsapp.data.remote.models.MovieResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.io.InputStreamReader

class JsonReader(private val context: Context) {
    // GSON object responsible for converting from JSON to a object
    private val gson = Gson()

    // InputStream representing .json
    private val inputStream: InputStream
        get() = context.resources.openRawResource(R.raw.movies)

    fun read(): List<Movie> {
        val itemType = object : TypeToken<MovieResponse>() {}.type
        val reader = InputStreamReader(inputStream)
        return gson.fromJson<MovieResponse>(reader, itemType).results.map {
            it
        }
    }
}
