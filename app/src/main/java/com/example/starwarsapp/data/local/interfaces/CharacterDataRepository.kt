package com.example.starwarsapp.data.local.interfaces

import android.content.Context
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.utils.Response

interface CharacterDataRepository {
    suspend fun getCharactersForMovieLocally(movie: MovieEntity): Response<List<CharacterEntity>>
    suspend fun getCharactersForMovieFromInternet(context: Context, charactersIds: String): Response<List<CharacterEntity>>
    suspend fun storeCharactersForMovie(charactersList: List<CharacterEntity>, movieId: Int): Response<Unit>
    suspend fun syncCharactersData(context: Context, movie: MovieEntity): Response<List<CharacterEntity>>
}
