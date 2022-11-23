package com.example.starwarsapp.data.remote.managers

import com.example.starwarsapp.data.remote.api.Swapi
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.remote.models.Movie
import com.example.starwarsapp.utils.Response

class SwapiManager(val service: Swapi) : SwapiRepository {
    override suspend fun getAllMovies(): Response<List<Movie>> {
        try {
            val response = service.getMovies()
            if (response.isSuccessful) {
                return Response.Success(response.body()?.results ?: listOf())
            }
        } catch (e: Exception) {
            return Response.Error(e.toString())
        }
        return Response.Error("null")
    }
}
