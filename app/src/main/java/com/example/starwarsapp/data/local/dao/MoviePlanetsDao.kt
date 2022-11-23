package com.example.starwarsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.starwarsapp.data.local.models.MoviePlanetsEntity

@Dao
interface MoviePlanetsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMoviePlanet(moviePlanetsEntity: MoviePlanetsEntity)
}
