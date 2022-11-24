package com.example.starwarsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.starwarsapp.data.local.models.MovieCharactersEntity

@Dao
interface MovieCharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieCharacter(movieCharactersEntity: MovieCharactersEntity)
}
