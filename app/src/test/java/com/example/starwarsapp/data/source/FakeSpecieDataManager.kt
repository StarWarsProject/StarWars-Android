package com.example.starwarsapp.data.source

import android.content.Context
import com.example.starwarsapp.data.local.models.SpecieEntity
import com.example.starwarsapp.data.sync.interfaces.SpecieDataRepository
import com.example.starwarsapp.utils.Response

class FakeSpecieDataManager : SpecieDataRepository {
    override suspend fun getEntitiesLocally(byPropertyName: String?, value: Any?): Response<List<SpecieEntity>> {
        return Response.Success(FakeData.species)
    }

    override suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<SpecieEntity>> {
        return Response.Success(FakeData.species)
    }

    override suspend fun storeData(dataList: List<SpecieEntity>, parentId: Int?): Response<Unit> {
        return Response.Success(Unit)
    }

    override suspend fun syncData(context: Context, idsString: String, filterPropertyName: String?, filterValue: Any?): Response<List<SpecieEntity>> {
        return Response.Success(FakeData.species)
    }
}
