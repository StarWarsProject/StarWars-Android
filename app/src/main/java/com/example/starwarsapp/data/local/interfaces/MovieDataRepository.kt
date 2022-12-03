package com.example.starwarsapp.data.local.interfaces

import android.content.Context
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.utils.Response

interface MovieDataRepository {
    suspend fun getAllMovies(context: Context): Response<List<MovieEntity>>
    suspend fun storeAllMovies(moviesList: List<MovieEntity>): Response<Unit>
    suspend fun getCharactersForMovie(context: Context, movie: MovieEntity): Response<List<CharacterEntity>>
    suspend fun storeCharactersForMovie(charactersList: List<CharacterEntity>, movieId: Int): Response<Unit>
}
