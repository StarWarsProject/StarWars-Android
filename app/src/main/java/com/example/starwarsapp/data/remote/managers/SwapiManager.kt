package com.example.starwarsapp.data.remote.managers

import android.util.Log
import com.example.starwarsapp.data.remote.api.Swapi
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.remote.models.Movie
import com.example.starwarsapp.data.remote.models.People
import com.example.starwarsapp.data.remote.models.Planet
import com.example.starwarsapp.utils.Response

class SwapiManager(private val service: Swapi) : SwapiRepository {
    override suspend fun getAllMovies(): Response<List<Movie>> {
        try {
            val response = service.getMovies()
            if (response.isSuccessful) {
                return Response.Success(response.body()?.results ?: listOf())
            }
            Log.e(Response.LOG_ERROR_TAG, response.message())
            return Response.Error(response.message())
        } catch (e: Exception) {
            Log.e(Response.LOG_ERROR_TAG, e.message.toString())
            return Response.Error(e.toString())
        }
    }

    override suspend fun getCharactersForMovie(characterUrlList: List<String>): Response<List<People>> {
        try {
            val data = characterUrlList.map {
                val response = service.getCharacterInformation(it)
                if (response.isSuccessful) {
                    return@map response.body() ?: People()
                } else {
                    Log.e(Response.LOG_ERROR_TAG, response.message())
                    return Response.Error(response.message())
                }
            }
            return Response.Success(data)
        } catch (e: Exception) {
            Log.e(Response.LOG_ERROR_TAG, e.message.toString())
            return Response.Error(e.toString())
        }
    }

    override suspend fun getPlanetsForMovie(planetUrlList: List<String>): Response<List<Planet>> {
        try {
            val data = planetUrlList.map {
                val response = service.getPlanetInformation(it)
                if (response.isSuccessful) {
                    return@map response.body() ?: Planet()
                } else {
                    Log.e(Response.LOG_ERROR_TAG, response.message())
                    return Response.Error(response.message())
                }
            }
            return Response.Success(data)
        } catch (e: Exception) {
            Log.e(Response.LOG_ERROR_TAG, e.message.toString())
            return Response.Error(e.toString())
        }
    }
}
