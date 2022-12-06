package com.example.starwarsapp.data.sync.interfaces

import android.content.Context
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.utils.Response

interface MovieDataRepository {
    suspend fun getAllMovies(context: Context): Response<List<MovieEntity>>
    suspend fun storeAllMovies(moviesList: List<MovieEntity>): Response<Unit>
    suspend fun getSingleMovie(movieId: Int): MovieEntity
}
