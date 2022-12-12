package com.example.starwarsapp.data.source

import android.content.Context
import com.example.starwarsapp.data.local.models.SpecieEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.remote.models.Specie
import com.example.starwarsapp.data.sync.interfaces.SpecieDataRepository
import com.example.starwarsapp.utils.Response

class FakeSpecieDataManager : SpecieDataRepository {
    override suspend fun getEntitiesLocally(byPropertyName: String?, value: Any?): Response<List<SpecieEntity>> {
        return Response.Success(FakeData.species)
    }

    override suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<IBaseRemoteData>> {
        val fake = FakeSwapiRepository()
        val sourceArray = listOf("ids")
        val remoteDataResponse = fake.getEntitiesForMovie(sourceArray, Specie::class.java)
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

    override suspend fun syncData(context: Context, idsString: String, filterPropertyName: String?, filterValue: Any?): Response<List<SpecieEntity>> {
        val fake = FakeSwapiRepository()
        val sourceArray = listOf("ids")
        val remoteDataResponse = fake.getEntitiesForMovie(sourceArray, Specie::class.java)
        if (FakeData.withData == 1) {
            remoteDataResponse.data?.let {
                it.map {
                    val data = listOf(it.toEntity())
                    data.let {
                        return Response.Success(it as List<SpecieEntity>)
                    }
                }
            }
        } else {
            return Response.Error("Something went wrong")
        }
        return Response.Success(listOf())
    }

    override suspend fun refreshData(context: Context, idsString: String): Response<List<SpecieEntity>> {
        val fake = FakeSwapiRepository()
        val sourceArray = listOf("ids")
        val remoteDataResponse = fake.getEntitiesForMovie(sourceArray, Specie::class.java)
        if (FakeData.withData == 1) {
            remoteDataResponse.data?.let {
                it.map {
                    val data = listOf(it.toEntity())
                    data.let {
                        return Response.Success(it as List<SpecieEntity>)
                    }
                }
            }
        } else {
            return Response.Error("Something went wrong")
        }
        return Response.Success(listOf())
    }
}
