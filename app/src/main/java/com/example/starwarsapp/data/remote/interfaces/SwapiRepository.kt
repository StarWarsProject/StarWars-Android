package com.example.starwarsapp.data.remote.interfaces

import com.example.starwarsapp.utils.Response

interface SwapiRepository {
    suspend fun getAllMovies(): Response<List<IBaseRemoteData>>
    suspend fun <T : IBaseRemoteData> getEntitiesForMovie(
        dataUrlList: List<String>,
        type: Class<T>
    ): Response<List<IBaseRemoteData>>
}
