package com.example.starwarsapp.data.local.managers

import android.content.Context
import android.util.Log
import com.example.starwarsapp.data.local.interfaces.CharacterDataRepository
import com.example.starwarsapp.data.local.interfaces.CharacterLocalRepository
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.utils.ListUtil
import com.example.starwarsapp.utils.Response
import com.example.starwarsapp.utils.Utils
import javax.inject.Inject

class CharacterDataManager
@Inject
constructor(private val swapiRepository: SwapiRepository, private val characterLocalRepository: CharacterLocalRepository) : CharacterDataRepository {
    override suspend fun getCharactersForMovieLocally(movie: MovieEntity): Response<List<CharacterEntity>> {
        Log.d("charman", "from db")
        val localDataMovies = characterLocalRepository.getCharactersForMovie(movie.id)
        return if (localDataMovies.isEmpty()) {
            Response.Error(Response.NO_DATA_AVAILABLE)
        } else {
            Response.Success(localDataMovies)
        }
    }

    override suspend fun getCharactersForMovieFromInternet(context: Context, charactersIds: String): Response<List<CharacterEntity>> {
        Log.d("charman", "from internet")
        return if (Utils.checkForInternet(context)) {
            val charactersList = ListUtil.joinedIdStringToArray(charactersIds)
            val apiDataCharacters = swapiRepository.getCharactersForMovie(charactersList)
            val data = apiDataCharacters.data
            if (data != null) {
                val entities = data.map {
                    it.toEntity()
                }
                return Response.Success(entities)
            } else {
                Response.Error(Response.NO_DATA_AVAILABLE)
            }
        } else {
            Response.Error(Response.NO_INTERNET)
        }
    }

    override suspend fun storeCharactersForMovie(charactersList: List<CharacterEntity>, movieId: Int): Response<Unit> {
        charactersList.forEach {
            characterLocalRepository.storeCharacterForMovie(it, movieId)
        }
        return Response.Success(Unit)
    }

    override suspend fun syncCharactersData(context: Context, movie: MovieEntity): Response<List<CharacterEntity>> {
        val charsEntities = getCharactersForMovieLocally(movie).data ?: listOf()
        val charsIds = ListUtil.joinedIdStringToArray(movie.characters)
        if (charsEntities.size != charsIds.count()) {
            val charsEntitiesIds = charsEntities.map { it.id.toString() }
            val missingCharsIds = charsIds.minus(charsEntitiesIds.toSet())
            val targetIds = if (charsEntities.size < charsIds.count()) {
                missingCharsIds.joinToString("@")
            } else {
                charsIds.joinToString("@")
            }
            val missingCharacters = getCharactersForMovieFromInternet(context, targetIds)
            if (missingCharacters.data != null) {
                storeCharactersForMovie(missingCharacters.data, movie.id)
                return if (charsEntities.size < charsIds.count()) {
                    Response.Success(charsEntities + (missingCharacters.data))
                } else {
                    Response.Success(missingCharacters.data)
                }
            }
            return Response.Error(Response.NO_INTERNET, charsEntities)
        } else {
            return Response.Success(charsEntities)
        }
    }
}
