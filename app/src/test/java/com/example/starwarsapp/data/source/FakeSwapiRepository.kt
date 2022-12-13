package com.example.starwarsapp.data.source

import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.remote.models.People
import com.example.starwarsapp.data.remote.models.Planet
import com.example.starwarsapp.data.remote.models.Specie
import com.example.starwarsapp.data.remote.models.Starship
import com.example.starwarsapp.utils.JsonReader
import com.example.starwarsapp.utils.Response

class FakeSwapiRepository : SwapiRepository {
    override suspend fun getAllMovies(): Response<List<IBaseRemoteData>> {
        try {
            if (!FakeData.withErrorData) {
                JsonReader.readMovies()?.let {
                    return Response.Success(it)
                }
            } else {
                JsonReader.readMoviesError()?.let {
                    return Response.Success(it)
                }
            }
            return Response.Error("file not found")
        } catch (e: Exception) {
            return Response.Error(e.toString())
        }
    }

    override suspend fun <T : IBaseRemoteData> getEntitiesForMovie(
        dataUrlList: List<String>,
        type: Class<T>
    ): Response<List<IBaseRemoteData>> {
        when (type) {
            People::class.java -> {
                if (!FakeData.withErrorData) {
                    JsonReader.readCharacter()?.let {
                        return Response.Success(it)
                    }
                } else {
                    return Response.Error("Something went wrong")
                }
            }
            Planet::class.java -> {
                if (!FakeData.withErrorData) {
                    JsonReader.readPlanet()?.let {
                        return Response.Success(it)
                    }
                } else {
                    return Response.Error("Something went wrong")
                }
            }
            Specie::class.java -> {
                if (!FakeData.withErrorData) {
                    JsonReader.readSpecie()?.let {
                        return Response.Success(it)
                    }
                } else {
                    return Response.Error("Something went wrong")
                }
            }
            Starship::class.java -> {
                if (!FakeData.withErrorData) {
                    JsonReader.readStarShip()?.let {
                        return Response.Success(it)
                    }
                } else {
                    return Response.Error("Something went wrong")
                }
            }
        }
        return Response.Error("Something went wrong")
    }
}
