package com.example.starwarsapp.data.source

import com.example.starwarsapp.StarWarsApplication
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.utils.JsonReader
import com.example.starwarsapp.utils.Response

class FakeSwapiRepository : SwapiRepository {
    override suspend fun getAllMovies(): Response<List<IBaseRemoteData>> {
        return try {
            val a = JsonReader(StarWarsApplication.context!!)
            Response.Success(a.read())
        } catch (e: Exception) {
            Response.Error(e.toString())
        }
    }

    override suspend fun <T : IBaseRemoteData> getEntitiesForMovie(
        dataUrlList: List<String>,
        type: Class<T>
    ): Response<List<IBaseRemoteData>> {
        TODO("Not yet implemented")
    }
}
