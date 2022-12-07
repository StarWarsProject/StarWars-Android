package com.example.starwarsapp.data.remote.managers

import android.util.Log
import com.example.starwarsapp.data.remote.api.Swapi
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.remote.models.People
import com.example.starwarsapp.data.remote.models.Planet
import com.example.starwarsapp.utils.Response

class SwapiManager(private val service: Swapi) : SwapiRepository {
    override suspend fun getAllMovies(): Response<List<IBaseRemoteData>> {
        try {
            val response = service.getMovies()
            if ((200..299).contains(response.code())) {
                return Response.Success(response.body()?.results ?: listOf())
            }
            Log.e(Response.LOG_ERROR_TAG, response.message())
            return Response.Error(response.message())
        } catch (e: Exception) {
            Log.e(Response.LOG_ERROR_TAG, e.toString())
            return Response.Error(e.toString())
        }
    }

    override suspend fun getCharactersForMovie(characterUrlList: List<String>): Response<List<IBaseRemoteData>> {
        try {
            val data = characterUrlList.map {
                val response = service.getCharacterInformation(it)
                if ((200..299).contains(response.code())) {
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

    override suspend fun getPlanetsForMovie(planetUrlList: List<String>): Response<List<IBaseRemoteData>> {
        try {
            val data = planetUrlList.map {
                val response = service.getPlanetInformation(it)
                if ((200..299).contains(response.code())) {
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
