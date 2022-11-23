package com.example.starwarsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.starwarsapp.data.local.models.MovieStarshipsEntity

@Dao
interface MovieStarshipsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieStarship(movieStarshipsEntity: MovieStarshipsEntity)
}
