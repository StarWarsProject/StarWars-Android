package com.example.starwarsapp.data.source

import android.content.Context
import com.example.starwarsapp.data.local.models.MovieEntity
import com.example.starwarsapp.data.sync.interfaces.MovieDataRepository
import com.example.starwarsapp.utils.Response

class FakeMovieDataManager : MovieDataRepository {
    override suspend fun getAllMovies(context: Context): Response<List<MovieEntity>> {
        val fake = FakeSwapiRepository()
        val apiDataMovies = fake.getAllMovies()
        val data = apiDataMovies.data
        return if (data != null && data.isNotEmpty()) {
            val entityList = data.map {
                it.toEntity() as MovieEntity
            }
            Response.Success(entityList)
        } else {
            Response.Error(Response.NO_DATA_AVAILABLE)
        }
    }

    override suspend fun storeAllMovies(moviesList: List<MovieEntity>): Response<Unit> {
        return Response.Success(Unit)
    }

    override suspend fun getSingleMovie(movieId: Int): MovieEntity {
        return FakeData.movie1
    }
}
