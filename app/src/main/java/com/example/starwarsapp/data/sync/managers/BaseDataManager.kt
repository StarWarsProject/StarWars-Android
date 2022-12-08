package com.example.starwarsapp.data.sync.managers

import android.content.Context
import android.util.Log
import com.example.starwarsapp.data.local.models.BaseEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.sync.interfaces.BaseDataRepository
import com.example.starwarsapp.data.sync.interfaces.BaseEntityCrud
import com.example.starwarsapp.utils.ListUtil
import com.example.starwarsapp.utils.Response
import com.example.starwarsapp.utils.Utils

open class BaseDataManager<T : BaseEntity> (
    private val baseCrudOperations: BaseEntityCrud<T>
) : BaseDataRepository<T> {
    companion object {
        const val LOGIDENTIFIER = "BaseDataManager"
    }
    override suspend fun getEntitiesLocally(byPropertyName: String?, value: Any?): Response<List<T>> {
        Log.d(LOGIDENTIFIER, "from db")
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

    override suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<IBaseRemoteData>> {
        Log.d(LOGIDENTIFIER, "from internet")
        return if (Utils.checkForInternet(context)) {
            val dataIdsList = ListUtil.joinedIdStringToArray(sourceStringIds)
            val remoteDataResponse = baseCrudOperations.getAllRemote(dataIdsList)
            val data = remoteDataResponse.data
            if (data != null) {
                return Response.Success(data)
            } else {
                Response.Error(Response.NO_DATA_AVAILABLE)
            }
        } else {
            Response.Error(Response.NO_INTERNET)
        }
    }

    override suspend fun storeData(dataList: List<IBaseRemoteData>): Response<Unit> {
        Log.d("someeee", dataList.count().toString())
        dataList.forEach {
            baseCrudOperations.storeSingleEntity(it)
        }
        return Response.Success(Unit)
    }

    override suspend fun syncData(context: Context, idsString: String, filterPropertyName: String?, filterValue: Any?): Response<List<T>> {
        val currentEntities = getEntitiesLocally(filterPropertyName, filterValue).data ?: listOf()
        val currentEntitiesIds = ListUtil.joinedIdStringToArray(idsString)
        Log.d("some", currentEntities.size.toString())
        Log.d("some", currentEntitiesIds.count().toString())
        if (currentEntities.size != currentEntitiesIds.count()) {
            val entitiesIds = currentEntities.map { it.id.toString() }
            val missingIds = currentEntitiesIds.minus(entitiesIds.toSet())
            Log.d("some", missingIds.toString())
            Log.d("some", "missingIds.toString()")
            val targetIds = if (currentEntities.size < currentEntitiesIds.count()) {
                missingIds.joinToString("@")
            } else {
                currentEntitiesIds.forEach {
                    baseCrudOperations.removeRelationWithParent(it.toInt(), filterValue.toString().toInt())
                }
                currentEntitiesIds.joinToString("@")
            }
            var missingEntities = listOf<T>()
            val missingData = getDataFromInternet(context, targetIds)
            missingData.data?.let { data ->
                storeData(data)
                missingEntities = data.map { it.toEntity() as T }
            }
            if (missingEntities.isNotEmpty()) {
                return if (currentEntities.size < currentEntitiesIds.count()) {
                    Response.Success(currentEntities + (missingEntities))
                } else {
                    Response.Success(missingEntities)
                }
            }
            return Response.Error(Response.NO_INTERNET, currentEntities)
        } else {
            return Response.Success(currentEntities)
        }
    }

    override suspend fun refreshData(context: Context, idsString: String): Response<List<T>> {
        val result = getDataFromInternet(context, idsString).data
        result?.let { data ->
            storeData(data)
            val entities = data.map { it.toEntity() }
            return Response.Success(entities as List<T>)
        } ?: run {
            return Response.Error(Response.NO_INTERNET)
        }
    }
}
