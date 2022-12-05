package com.example.starwarsapp.data.local.managers

import android.content.Context
import com.example.starwarsapp.data.local.interfaces.MovieDataRepository
import com.example.starwarsapp.data.local.interfaces.MovieLocalRepository
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.utils.ListUtil
import com.example.starwarsapp.utils.Response
import com.example.starwarsapp.utils.Utils
import javax.inject.Inject

class MovieDataManager
@Inject
constructor(private val swapiRepository: SwapiRepository, private val movieLocalRepository: MovieLocalRepository) : MovieDataRepository {
    override suspend fun getAllMovies(context: Context): Response<List<MovieEntity>> {
        return if (Utils.checkForInternet(context)) {
            val apiDataMovies = swapiRepository.getAllMovies()
            val data = apiDataMovies.data
            if (data != null) {
                val entityList = data.map {
                    it.toEntity()
                }
                return Response.Success(entityList)
            } else {
                return Response.Error(Response.NO_DATA_AVAILABLE)
            }
        } else {
            val localDataMovies = movieLocalRepository.getLocalMovies()
            if (localDataMovies.isEmpty()) {
                Response.Error(Response.NO_DATA_AVAILABLE)
            } else {
                Response.Success(localDataMovies)
            }
        }
    }

    override suspend fun storeAllMovies(moviesList: List<MovieEntity>): Response<Unit> {
        moviesList.forEach {
            movieLocalRepository.addLocalMovies(it)
        }
        return Response.Success(Unit)
    }

    override suspend fun getCharactersForMovie(context: Context, movie: MovieEntity): Response<List<CharacterEntity>> {
        return if (Utils.checkForInternet(context)) {
            val charactersList = ListUtil.joinedIdStringToArray(movie.characters)
            val apiDataCharacters = swapiRepository.getCharactersForMovie(charactersList)
            val data = apiDataCharacters.data
            if (data != null) {
                val entityList = data.map {
                    it.toEntity()
                }
                return Response.Success(entityList)
            } else {
                return Response.Error(Response.NO_DATA_AVAILABLE)
            }
        } else {
            val localDataMovies = movieLocalRepository.getCharactersForMovie(movie.id)
            if (localDataMovies.isEmpty()) {
                Response.Error(Response.NO_DATA_AVAILABLE)
            } else {
                Response.Success(localDataMovies)
            }
        }
    }

    override suspend fun storeCharactersForMovie(charactersList: List<CharacterEntity>, movieId: Int): Response<Unit> {
        charactersList.forEach {
            movieLocalRepository.storeCharacterForMovie(it, movieId)
        }
        return Response.Success(Unit)
    }
}
