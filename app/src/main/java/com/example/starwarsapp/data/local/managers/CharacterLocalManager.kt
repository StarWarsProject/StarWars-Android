package com.example.starwarsapp.data.local.managers

import com.example.starwarsapp.data.local.db.StarWarsDB
import com.example.starwarsapp.data.local.interfaces.CharacterLocalRepository
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.local.models.MovieCharactersEntity
import com.example.starwarsapp.data.remote.models.People
import com.example.starwarsapp.utils.ListUtil

class CharacterLocalManager(private val localStarWarsDB: StarWarsDB) : CharacterLocalRepository {
    override suspend fun getCharactersForMovie(movieId: Int): List<CharacterEntity> {
        return localStarWarsDB.movieCharactersDao().getCharacters(movieId)
    }

    override suspend fun storeCharacterForMovie(characterData: People) {
        val characterEnt = characterData.toEntity()
        localStarWarsDB.characterDao().addCharacter(characterData.toEntity())
        characterData.films.forEach {
            localStarWarsDB.movieCharactersDao().addMovieCharacter(MovieCharactersEntity(ListUtil.splitToGetIdFromUrl(it).toInt(), characterEnt.id))
        }
    }

    override suspend fun removeCharacterFromMovie(charId: Int, movieId: Int) {
        localStarWarsDB.movieCharactersDao().removeCharacterFromMovie(charId, movieId)
    }
}
