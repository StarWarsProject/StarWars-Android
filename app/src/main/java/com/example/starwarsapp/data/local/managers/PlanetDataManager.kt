package com.example.starwarsapp.data.local.managers

import android.content.Context
import android.util.Log
import com.example.starwarsapp.data.local.interfaces.PlanetDataRepository
import com.example.starwarsapp.data.local.interfaces.PlanetLocalRepository
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.data.local.models.PlanetEntity
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.utils.ListUtil
import com.example.starwarsapp.utils.Response
import com.example.starwarsapp.utils.Utils
import javax.inject.Inject

class PlanetDataManager
@Inject
constructor(private val swapiRepository: SwapiRepository, private val planetLocalRepository: PlanetLocalRepository) : PlanetDataRepository {
    override suspend fun getPlanetsForMovieLocally(movie: MovieEntity): Response<List<PlanetEntity>> {
        Log.d("plaman", "from db")
        val localDataMovies = planetLocalRepository.getPlanetsForMovie(movie.id)
        return if (localDataMovies.isEmpty()) {
            Response.Error(Response.NO_DATA_AVAILABLE)
        } else {
            Response.Success(localDataMovies)
        }
    }

    override suspend fun getPlanetsForMovieFromInternet(context: Context, planetsIds: String): Response<List<PlanetEntity>> {
        Log.d("plaman", "from internet")
        return if (Utils.checkForInternet(context)) {
            val planetsList = ListUtil.joinedIdStringToArray(planetsIds)
            val apiDataPlanets = swapiRepository.getPlanetsForMovie(planetsList)
            val data = apiDataPlanets.data
            if (data != null) {
                val entities = data.map {
                    it.toEntity()
                }
                return Response.Success(entities)
            } else {
                Response.Error(Response.NO_DATA_AVAILABLE)
            }
        } else {
            Response.Error(Response.NO_INTERNET)
        }
    }

    override suspend fun storePlanetsForMovie(planetsList: List<PlanetEntity>, movieId: Int): Response<Unit> {
        planetsList.forEach {
            planetLocalRepository.storePlanetForMovie(it, movieId)
        }
        return Response.Success(Unit)
    }

    override suspend fun syncCPlanetsData(context: Context, movie: MovieEntity): Response<List<PlanetEntity>> {
        val planEntities = getPlanetsForMovieLocally(movie).data ?: listOf()
        val planIds = ListUtil.joinedIdStringToArray(movie.characters)
        if (planEntities.size != planIds.count()) {
            val planEntitiesIds = planEntities.map { it.id.toString() }
            val missingPlanIds = planIds.minus(planEntitiesIds.toSet())
            val targetIds = if (planEntities.size < planIds.count()) {
                missingPlanIds.joinToString("@")
            } else {
                planIds.joinToString("@")
            }
            val missingPlanets = getPlanetsForMovieFromInternet(context, targetIds)
            if (missingPlanets.data != null) {
                storePlanetsForMovie(missingPlanets.data, movie.id)
                return if (planEntities.size < planIds.count()) {
                    Response.Success(planEntities + (missingPlanets.data))
                } else {
                    Response.Success(missingPlanets.data)
                }
            }
            return Response.Error(Response.NO_INTERNET, planEntities)
        } else {
            return Response.Success(planEntities)
        }
    }
}
