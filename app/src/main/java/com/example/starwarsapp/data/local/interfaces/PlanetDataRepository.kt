package com.example.starwarsapp.data.local.interfaces

import android.content.Context
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.data.local.models.PlanetEntity
import com.example.starwarsapp.utils.Response

interface PlanetDataRepository {
    suspend fun getPlanetsForMovieLocally(movie: MovieEntity): Response<List<PlanetEntity>>
    suspend fun getPlanetsForMovieFromInternet(context: Context, planetsIds: String): Response<List<PlanetEntity>>
    suspend fun storePlanetsForMovie(planetsList: List<PlanetEntity>, movieId: Int): Response<Unit>
    suspend fun syncCPlanetsData(context: Context, movie: MovieEntity): Response<List<PlanetEntity>>
}
