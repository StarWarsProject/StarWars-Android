package com.example.starwarsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.starwarsapp.data.local.models.MovieSpeciesEntity

@Dao
interface MovieSpeciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieSpecie(movieSpeciesEntity: MovieSpeciesEntity)
}
