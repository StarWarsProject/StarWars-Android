package com.example.starwarsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.data.local.models.MoviePlanetsEntity
import com.example.starwarsapp.data.local.models.PlanetEntity

@Dao
interface MoviePlanetsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMoviePlanet(moviePlanetsEntity: MoviePlanetsEntity)

    @Query(
        "SELECT a.* FROM PlanetEntity as a, MoviePlanetsEntity as b WHERE a.id = b.planetId " + "AND b.movieId = :movieId"
    )
    suspend fun getPlanets(movieId: Int): List<PlanetEntity>
}
