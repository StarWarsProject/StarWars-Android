package com.example.starwarsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.starwarsapp.data.local.models.MovieVehiclesEntity

@Dao
interface MovieVehiclesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieVehicle(movieVehiclesEntity: MovieVehiclesEntity)
}
