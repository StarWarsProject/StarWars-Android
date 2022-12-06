package com.example.starwarsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.data.local.models.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM movieentity")
    fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE id = :movieId")
    fun getSingleMovie(movieId: Int): MovieEntity
}
