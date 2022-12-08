package com.example.starwarsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.local.models.MovieCharactersEntity

@Dao
interface MovieCharactersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovieCharacter(movieCharactersEntity: MovieCharactersEntity)

    @Query(
        "SELECT a.* FROM CharacterEntity as a, MovieCharactersEntity as b WHERE a.id = b.characterId " + "AND b.movieId = :movieId"
    )
    suspend fun getCharacters(movieId: Int): List<CharacterEntity>
}
