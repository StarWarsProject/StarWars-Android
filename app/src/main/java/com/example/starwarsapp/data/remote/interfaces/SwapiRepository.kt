package com.example.starwarsapp.data.remote.interfaces

import com.example.starwarsapp.utils.Response

interface SwapiRepository {
    suspend fun getAllMovies(): Response<List<IBaseRemoteData>>
    suspend fun <T : IBaseRemoteData> getEntitiesForMovie(
        dataUrlList: List<String>,
        type: Class<T>
    ): Response<List<IBaseRemoteData>>
//    suspend fun getCharactersForMovie(characterUrlList: List<String>): Response<List<IBaseRemoteData>>
//    suspend fun getPlanetsForMovie(planetUrlList: List<String>): Response<List<IBaseRemoteData>>
//    suspend fun getSpeciesForMovie(specieUrlList: List<String>): Response<List<IBaseRemoteData>>
}
