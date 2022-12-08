package com.example.starwarsapp.data.source

import android.content.Context
import com.example.starwarsapp.data.local.models.PlanetEntity
import com.example.starwarsapp.data.sync.interfaces.PlanetDataRepository
import com.example.starwarsapp.utils.Response

class FakePlanetDataManager : PlanetDataRepository {
    override suspend fun getEntitiesLocally(byPropertyName: String?, value: Any?): Response<List<PlanetEntity>> {
        return Response.Success(FakeData.planets)
    }

    override suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<PlanetEntity>> {
        return Response.Success(FakeData.planets)
    }

    override suspend fun storeData(dataList: List<PlanetEntity>, parentId: Int?): Response<Unit> {
        return Response.Success(Unit)
    }

    override suspend fun syncData(context: Context, idsString: String, filterPropertyName: String?, filterValue: Any?): Response<List<PlanetEntity>> {
        return Response.Success(FakeData.planets)
    }
}
