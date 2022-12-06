package com.example.starwarsapp.data.sync.managers

import android.content.Context
import android.util.Log
import com.example.starwarsapp.data.local.models.BaseEntity
import com.example.starwarsapp.data.sync.interfaces.BaseDataRepository
import com.example.starwarsapp.data.sync.interfaces.BaseEntityCrud
import com.example.starwarsapp.utils.ListUtil
import com.example.starwarsapp.utils.Response
import com.example.starwarsapp.utils.Utils

open class BaseDataManager<T : BaseEntity> (
    private val baseCrudOperations: BaseEntityCrud<T>
) : BaseDataRepository<T> {
    override suspend fun getEntitiesLocally(byPropertyName: String?, value: Any?): Response<List<T>> {
        Log.d("baseMan", "from db")
        val localData = baseCrudOperations.getAllLocal(byPropertyName, value).data
        return if (localData != null) {
            if (localData.isEmpty()) {
                Response.Error(Response.NO_DATA_AVAILABLE)
            } else {
                Response.Success(localData)
            }
        } else {
            Response.Error(Response.DATABASE_ERROR)
        }
    }

    override suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<T>> {
        Log.d("baseMan", "from internet")
        return if (Utils.checkForInternet(context)) {
            val dataIdsList = ListUtil.joinedIdStringToArray(sourceStringIds)
            val remoteDataResponse = baseCrudOperations.getAllRemote(dataIdsList)
            val data = remoteDataResponse.data
            if (data != null) {
                val currentEntities = data.map {
                    it.toEntity() as T
                }
                return Response.Success(currentEntities)
            } else {
                Response.Error(Response.NO_DATA_AVAILABLE)
            }
        } else {
            Response.Error(Response.NO_INTERNET)
        }
    }

    override suspend fun storeData(dataList: List<T>, parentId: Int?): Response<Unit> {
        dataList.forEach {
            if (parentId != null) {
                baseCrudOperations.storeSingleEntity(it, parentId)
            }
        }
        return Response.Success(Unit)
    }

    override suspend fun syncData(context: Context, idsString: String, filterPropertyName: String?, filterValue: Any?): Response<List<T>> {
        val currentEntities = getEntitiesLocally(filterPropertyName, filterValue).data ?: listOf()
        val currentEntitiesIds = ListUtil.joinedIdStringToArray(idsString)
        if (currentEntities.size != currentEntitiesIds.count()) {
            val entitiesIds = currentEntities.map { it.id.toString() }
            val missingIds = currentEntitiesIds.minus(entitiesIds.toSet())
            val targetIds = if (currentEntities.size < currentEntitiesIds.count()) {
                missingIds.joinToString("@")
            } else {
                currentEntitiesIds.joinToString("@")
            }
            val missingEntities = getDataFromInternet(context, targetIds)
            if (missingEntities.data != null) {
                if (filterValue != null) {
                    storeData(missingEntities.data, filterValue as Int)
                }
                return if (currentEntities.size < currentEntitiesIds.count()) {
                    Response.Success(currentEntities + (missingEntities.data))
                } else {
                    Response.Success(missingEntities.data)
                }
            }
            return Response.Error(Response.NO_INTERNET, currentEntities)
        } else {
            return Response.Success(currentEntities)
        }
    }
}
