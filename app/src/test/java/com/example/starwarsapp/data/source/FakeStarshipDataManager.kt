package com.example.starwarsapp.data.source

import android.content.Context
import com.example.starwarsapp.data.local.models.StarshipEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.remote.models.Starship
import com.example.starwarsapp.data.sync.interfaces.StarshipDataRepository
import com.example.starwarsapp.utils.Response

class FakeStarshipDataManager : StarshipDataRepository {
    override suspend fun getEntitiesLocally(byPropertyName: String?, value: Any?): Response<List<StarshipEntity>> {
        return Response.Success(FakeData.starships)
    }

    override suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<IBaseRemoteData>> {
        val fake = FakeSwapiRepository()
        val sourceArray = listOf("ids")
        val remoteDataResponse = fake.getEntitiesForMovie(sourceArray, Starship::class.java)
        if (FakeData.withData == 1) {
            remoteDataResponse.data?.let {
                return Response.Success(it)
            }
        } else {
            return Response.Success(listOf())
        }
        return Response.Success(listOf())
    }

    override suspend fun storeData(dataList: List<IBaseRemoteData>): Response<Unit> {
        return Response.Success(Unit)
    }

    override suspend fun syncData(context: Context, idsString: String, filterPropertyName: String?, filterValue: Any?): Response<List<StarshipEntity>> {
        val fake = FakeSwapiRepository()
        val sourceArray = listOf("ids")
        val remoteDataResponse = fake.getEntitiesForMovie(sourceArray, Starship::class.java)
        if (FakeData.withData == 1) {
            remoteDataResponse.data?.let {
                it.map {
                    val data = listOf(it.toEntity())
                    data.let {
                        return Response.Success(it as List<StarshipEntity>)
                    }
                }
            }
        } else {
            return Response.Success(listOf())
        }
        return Response.Success(listOf())
    }

    override suspend fun refreshData(context: Context, idsString: String): Response<List<StarshipEntity>> {
        val fake = FakeSwapiRepository()
        val sourceArray = listOf("ids")
        val remoteDataResponse = fake.getEntitiesForMovie(sourceArray, Starship::class.java)
        if (FakeData.withData == 1) {
            remoteDataResponse.data?.let {
                it.map {
                    val data = listOf(it.toEntity())
                    data.let {
                        return Response.Success(it as List<StarshipEntity>)
                    }
                }
            }
        } else {
            return Response.Success(listOf())
        }
        return Response.Success(listOf())
    }
}
