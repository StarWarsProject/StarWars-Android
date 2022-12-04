package com.example.starwarsapp.data.local.managers

import com.example.starwarsapp.data.local.db.StarWarsDB
import com.example.starwarsapp.data.local.interfaces.CharacterLocalRepository
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.local.models.MovieCharactersEntity

class CharacterLocalManager(private val localStarWarsDB: StarWarsDB) : CharacterLocalRepository {
    override fun getCharactersForMovie(movieId: Int): List<CharacterEntity> {
        return localStarWarsDB.movieCharactersDao().getCharacters(movieId)
    }

    override fun storeCharacterForMovie(characterEntity: CharacterEntity, movieId: Int) {
        localStarWarsDB.characterDao().addCharacter(characterEntity)
        localStarWarsDB.movieCharactersDao().addMovieCharacter(MovieCharactersEntity(movieId, characterEntity.id))
    }
}
