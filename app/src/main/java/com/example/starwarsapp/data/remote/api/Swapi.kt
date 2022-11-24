package com.example.starwarsapp.data.remote.api

import com.example.starwarsapp.data.remote.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface Swapi {
    @GET("api/films/")
    suspend fun getMovies(): Response<MovieResponse>
}
