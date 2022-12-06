package com.example.starwarsapp.data.remote.api

import com.example.starwarsapp.data.remote.models.MovieResponse
import com.example.starwarsapp.data.remote.models.People
import com.example.starwarsapp.data.remote.models.Planet
import com.example.starwarsapp.data.remote.models.Specie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Swapi {
    @GET("api/films/")
    suspend fun getMovies(): Response<MovieResponse>

    @GET("api/people/{id}")
    suspend fun getCharacterInformation(@Path("id") id: String): Response<People>

    @GET("api/planets/{id}")
    suspend fun getPlanetInformation(@Path("id") id: String): Response<Planet>

    @GET("api/species/{id}")
    suspend fun getSpecieInformation(@Path("id") id: String): Response<Specie>
}
