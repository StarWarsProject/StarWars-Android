package com.example.starwarsapp.data.source

import android.content.Context
import com.example.starwarsapp.data.local.models.StarshipEntity
import com.example.starwarsapp.data.sync.interfaces.StarshipDataRepository
import com.example.starwarsapp.utils.Response

class FakeStarshipDataManager : StarshipDataRepository {
    override suspend fun getEntitiesLocally(byPropertyName: String?, value: Any?): Response<List<StarshipEntity>> {
        return Response.Success(FakeData.starships)
    }

    override suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<StarshipEntity>> {
        return Response.Success(FakeData.starships)
    }

    override suspend fun storeData(dataList: List<StarshipEntity>, parentId: Int?): Response<Unit> {
        return Response.Success(Unit)
    }

    override suspend fun syncData(context: Context, idsString: String, filterPropertyName: String?, filterValue: Any?): Response<List<StarshipEntity>> {
        return Response.Success(FakeData.starships)
    }
}
