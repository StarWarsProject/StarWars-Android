package com.example.starwarsapp.data.source

import android.content.Context
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.sync.interfaces.CharacterDataRepository
import com.example.starwarsapp.utils.Response

class FakeCharacterDataManager : CharacterDataRepository {
    override suspend fun getEntitiesLocally(byPropertyName: String?, value: Any?): Response<List<CharacterEntity>> {
        return Response.Success(FakeData.characters)
    }

    override suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<CharacterEntity>> {
        return Response.Success(FakeData.characters)
    }

    override suspend fun storeData(dataList: List<CharacterEntity>, parentId: Int?): Response<Unit> {
        return Response.Success(Unit)
    }

    override suspend fun syncData(context: Context, idsString: String, filterPropertyName: String?, filterValue: Any?): Response<List<CharacterEntity>> {
        return Response.Success(FakeData.characters)
    }
}
