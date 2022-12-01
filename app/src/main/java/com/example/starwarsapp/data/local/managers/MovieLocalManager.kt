package com.example.starwarsapp.data.local.managers

import com.example.starwarsapp.data.local.db.StarWarsDB
import com.example.starwarsapp.data.local.interfaces.MovieLocalRepository
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.local.models.MovieCharactersEntity
import com.example.starwarsapp.data.local.models.MovieEntity

class MovieLocalManager(private val localStarWarsDB: StarWarsDB) : MovieLocalRepository {
    override fun addLocalMovies(movieEntity: MovieEntity) {
        localStarWarsDB.movieDao().addMovie(movieEntity)
    }

    override fun getLocalMovies(): List<MovieEntity> {
        return localStarWarsDB.movieDao().getAllMovies()
    }

    override fun getCharactersForMovie(movieId: Int): List<CharacterEntity> {
        return localStarWarsDB.movieCharactersDao().getCharacters(movieId)
    }

    override fun storeCharacterForMovie(characterEntity: CharacterEntity, movieId: Int) {
        localStarWarsDB.characterDao().addCharacter(characterEntity)
        localStarWarsDB.movieCharactersDao().addMovieCharacter(MovieCharactersEntity(movieId, characterEntity.id))
    }
}
