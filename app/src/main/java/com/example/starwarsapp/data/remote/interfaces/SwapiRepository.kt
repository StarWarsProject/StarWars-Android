package com.example.starwarsapp.data.remote.interfaces

import com.example.starwarsapp.utils.Response

interface SwapiRepository {
    suspend fun getAllMovies(): Response<List<IBaseRemoteData>>
    suspend fun getCharactersForMovie(characterUrlList: List<String>): Response<List<IBaseRemoteData>>
}
