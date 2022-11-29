package com.example.starwarsapp.data.remote.interfaces

import com.example.starwarsapp.data.remote.models.Movie
import com.example.starwarsapp.data.remote.models.People
import com.example.starwarsapp.utils.Response

interface SwapiRepository {
    suspend fun getAllMovies(): Response<List<Movie>>
    suspend fun getCharactersForMovie(charaterUrlList: List<String>): Response<List<People>>
}
