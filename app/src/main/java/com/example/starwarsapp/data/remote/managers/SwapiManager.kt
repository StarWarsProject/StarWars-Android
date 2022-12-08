package com.example.starwarsapp.data.remote.managers

import android.util.Log
import com.example.starwarsapp.data.remote.api.Swapi
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.remote.models.People
import com.example.starwarsapp.data.remote.models.Planet
import com.example.starwarsapp.data.remote.models.Specie
import com.example.starwarsapp.data.remote.models.Starship
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

    override suspend fun <T : IBaseRemoteData> getEntitiesForMovie(
        dataUrlList: List<String>,
        type: Class<T>
    ): Response<List<IBaseRemoteData>> {
        try {
            var response: retrofit2.Response<T>? = null
            val data = dataUrlList.map {
                when (type) {
                    People::class.java -> {
                        response = service.getCharacterInformation(it) as retrofit2.Response<T>
                    }
                    Planet::class.java -> {
                        response = service.getPlanetInformation(it) as retrofit2.Response<T>
                    }
                    Specie::class.java -> {
                        response = service.getSpecieInformation(it) as retrofit2.Response<T>
                    }
                    Starship::class.java -> {
                        response = service.getStarshipInformation(it) as retrofit2.Response<T>
                    }
                }
                val resp = response ?: return Response.Error("Something went wrong")
                if ((200..299).contains(resp.code())) {
                    return@map resp.body() as T
                } else {
                    Log.e(Response.LOG_ERROR_TAG, resp.message())
                    return Response.Error(resp.message())
                }
            }

            return Response.Success(data)
        } catch (e: Exception) {
            Log.e(Response.LOG_ERROR_TAG, e.message.toString())
            return Response.Error(e.toString())
        }
    }
}
