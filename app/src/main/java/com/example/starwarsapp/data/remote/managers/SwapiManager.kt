package com.example.starwarsapp.data.remote.managers

import com.example.starwarsapp.data.remote.api.Swapi
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.remote.models.Movie
import com.example.starwarsapp.data.remote.models.People
import com.example.starwarsapp.utils.Response

class SwapiManager(private val service: Swapi) : SwapiRepository {
    override suspend fun getAllMovies(): Response<List<Movie>> {
        try {
            val response = service.getMovies()
            if (response.isSuccessful) {
                return Response.Success(response.body()?.results ?: listOf())
            }
            return Response.Error(response.message())
        } catch (e: Exception) {
            return Response.Error(e.toString())
        }
    }

    override suspend fun getCharactersForMovie(charaterUrlList: List<String>): Response<List<People>> {
        try {
            val data = charaterUrlList.map {
                val response = service.getCharacterInformation(it)
                if (response.isSuccessful) {
                    return@map response.body() ?: People()
                } else {
                    return Response.Error(response.message())
                }
            }
            return Response.Success(data)
        } catch (e: Exception) {
            return Response.Error(e.toString())
        }
    }
}
