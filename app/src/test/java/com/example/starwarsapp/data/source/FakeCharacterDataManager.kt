package com.example.starwarsapp.data.source

import android.content.Context
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.remote.models.People
import com.example.starwarsapp.data.sync.interfaces.CharacterDataRepository
import com.example.starwarsapp.utils.Response

class FakeCharacterDataManager : CharacterDataRepository {
    override suspend fun getEntitiesLocally(byPropertyName: String?, value: Any?): Response<List<CharacterEntity>> {
        return Response.Success(FakeData.characters)
    }

    override suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<IBaseRemoteData>> {
        val fake = FakeSwapiRepository()
        val sourceArray = listOf("ids")
        val remoteDataResponse = fake.getEntitiesForMovie(sourceArray, People::class.java)
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

    override suspend fun syncData(context: Context, idsString: String, filterPropertyName: String?, filterValue: Any?): Response<List<CharacterEntity>> {
        val fake = FakeSwapiRepository()
        val sourceArray = listOf("ids")
        val remoteDataResponse = fake.getEntitiesForMovie(sourceArray, People::class.java)
        if (FakeData.withData == 1) {
            remoteDataResponse.data?.let {
                it.map {
                    val data = listOf(it.toEntity())
                    data.let {
                        return Response.Success(it as List<CharacterEntity>)
                    }
                }
            }
        } else {
            return Response.Success(listOf())
        }
        return Response.Success(listOf())
    }

    override suspend fun refreshData(context: Context, idsString: String): Response<List<CharacterEntity>> {
        val fake = FakeSwapiRepository()
        val sourceArray = listOf("ids")
        val remoteDataResponse = fake.getEntitiesForMovie(sourceArray, People::class.java)
        if (FakeData.withData == 1) {
            remoteDataResponse.data?.let {
                it.map {
                    val data = listOf(it.toEntity())
                    data.let {
                        return Response.Success(it as List<CharacterEntity>)
                    }
                }
            }
        } else {
            return Response.Success(listOf())
        }
        return Response.Success(listOf())
    }
}
