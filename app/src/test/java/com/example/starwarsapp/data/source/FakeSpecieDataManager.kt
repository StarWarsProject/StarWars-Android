package com.example.starwarsapp.data.source

import android.content.Context
import com.example.starwarsapp.data.local.models.SpecieEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.sync.interfaces.SpecieDataRepository
import com.example.starwarsapp.utils.Response

class FakeSpecieDataManager : SpecieDataRepository {
    override suspend fun getEntitiesLocally(byPropertyName: String?, value: Any?): Response<List<SpecieEntity>> {
        return Response.Success(FakeData.species)
    }

    override suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<IBaseRemoteData>> {
        return Response.Success(FakeData.specieResponse)
    }

    override suspend fun storeData(dataList: List<IBaseRemoteData>): Response<Unit> {
        return Response.Success(Unit)
    }

    override suspend fun syncData(context: Context, idsString: String, filterPropertyName: String?, filterValue: Any?): Response<List<SpecieEntity>> {
        return Response.Success(FakeData.species)
    }

    override suspend fun refreshData(context: Context, idsString: String): Response<List<SpecieEntity>> {
        return Response.Success(FakeData.species)
    }
}
