package com.example.starwarsapp.data.source

import android.content.Context
import com.example.starwarsapp.data.local.models.PlanetEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.remote.models.Planet
import com.example.starwarsapp.data.sync.interfaces.PlanetDataRepository
import com.example.starwarsapp.utils.Response

class FakePlanetDataManager : PlanetDataRepository {
    override suspend fun getEntitiesLocally(byPropertyName: String?, value: Any?): Response<List<PlanetEntity>> {
        return Response.Success(FakeData.planets)
    }

    override suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<IBaseRemoteData>> {
        val fake = FakeSwapiRepository()
        val sourceArray = listOf("ids")
        val remoteDataResponse = fake.getEntitiesForMovie(sourceArray, Planet::class.java)
        if (FakeData.withData == 1) {
            remoteDataResponse.data?.let {
                return Response.Success(it)
            }
        } else {
            return Response.Error("Something went wrong")
        }
        return Response.Success(listOf())
    }

    override suspend fun storeData(dataList: List<IBaseRemoteData>): Response<Unit> {
        return Response.Success(Unit)
    }

    override suspend fun syncData(context: Context, idsString: String, filterPropertyName: String?, filterValue: Any?): Response<List<PlanetEntity>> {
        val fake = FakeSwapiRepository()
        val sourceArray = listOf("ids")
        val remoteDataResponse = fake.getEntitiesForMovie(sourceArray, Planet::class.java)
        if (FakeData.withData == 1) {
            remoteDataResponse.data?.let {
                it.map {
                    val data = listOf(it.toEntity())
                    data.let {
                        return Response.Success(it as List<PlanetEntity>)
                    }
                }
            }
        } else {
            return Response.Error("Something went wrong")
        }
        return Response.Success(listOf())
    }

    override suspend fun refreshData(context: Context, idsString: String): Response<List<PlanetEntity>> {
        val fake = FakeSwapiRepository()
        val sourceArray = listOf("ids")
        val remoteDataResponse = fake.getEntitiesForMovie(sourceArray, Planet::class.java)
        if (FakeData.withData == 1) {
            remoteDataResponse.data?.let {
                it.map {
                    val data = listOf(it.toEntity())
                    data.let {
                        return Response.Success(it as List<PlanetEntity>)
                    }
                }
            }
        } else {
            return Response.Error("Something went wrong")
        }
        return Response.Success(listOf())
    }
}
