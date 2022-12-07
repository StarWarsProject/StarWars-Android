package com.example.starwarsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.data.local.models.MovieSpeciesEntity
import com.example.starwarsapp.data.local.models.SpecieEntity

@Dao
interface MovieSpeciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieSpecie(movieSpeciesEntity: MovieSpeciesEntity)

    @Query(
        "SELECT a.* FROM SpecieEntity as a, MovieSpeciesEntity as b WHERE a.id = b.specieId " + "AND b.movieId = :movieId"
    )
    suspend fun getSpecies(movieId: Int): List<SpecieEntity>
}
