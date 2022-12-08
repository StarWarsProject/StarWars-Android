package com.example.starwarsapp.data.source

import android.content.Context
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.data.sync.interfaces.MovieDataRepository
import com.example.starwarsapp.utils.Response

class FakeMovieDataManager : MovieDataRepository {
    override suspend fun getAllMovies(context: Context): Response<List<MovieEntity>> {
        return Response.Success(FakeData.movies)
    }

    override suspend fun storeAllMovies(moviesList: List<MovieEntity>): Response<Unit> {
        return Response.Success(Unit)
    }

    override suspend fun getSingleMovie(movieId: Int): MovieEntity {
        return FakeData.movie1
    }
}
