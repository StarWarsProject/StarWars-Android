package com.example.starwarsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.data.local.models.MovieStarshipsEntity
import com.example.starwarsapp.data.local.models.StarshipEntity

@Dao
interface MovieStarshipsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieStarship(movieStarshipsEntity: MovieStarshipsEntity)

    @Query(
        "SELECT a.* FROM StarshipEntity as a, MovieStarshipsEntity as b WHERE a.id = b.starshipId " + "AND b.movieId = :movieId"
    )
    fun getStarships(movieId: Int): List<StarshipEntity>
}
