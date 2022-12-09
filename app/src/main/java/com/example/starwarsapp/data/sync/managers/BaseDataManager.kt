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

open class BaseDataManager<T : BaseEntity>(
    private val baseCrudOperations: BaseEntityCrud<T>
) : BaseDataRepository<T> {
    companion object {
        const val LOGIDENTIFIER = "BaseDataManager"
    }

    override suspend fun getEntitiesLocally(
        byPropertyName: String?,
        value: Any?
    ): Response<List<T>> {
        Log.d(LOGIDENTIFIER, "from db")
        return when (val response = baseCrudOperations.getAllLocal(byPropertyName, value)) {
            is Response.Success -> {
                val localData = response.data!!
                if (localData.isEmpty()) {
                    Response.Error(Response.NO_DATA_AVAILABLE)
                } else {
                    Response.Success(localData)
                }
            }
            is Response.Error -> {
                Response.Error(Response.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getDataFromInternet(
        context: Context,
        sourceStringIds: String
    ): Response<List<IBaseRemoteData>> {
        Log.d(LOGIDENTIFIER, "from internet")
        return if (Utils.checkForInternet(context)) {
            val dataIdsList = ListUtil.joinedIdStringToArray(sourceStringIds)
            when (val remoteDataResponse = baseCrudOperations.getAllRemote(dataIdsList)) {
                is Response.Error -> {
                    return Response.Error(Response.NO_DATA_AVAILABLE)
                }
                is Response.Success -> {
                    remoteDataResponse.data?.let {
                        return Response.Success(it)
                    } ?: run {
                        return Response.Error(Response.NO_DATA_AVAILABLE)
                    }
                }
            }
        } else {
            Response.Error(Response.NO_INTERNET)
        }
    }

    override suspend fun storeData(dataList: List<IBaseRemoteData>): Response<Unit> {
        Log.d("someeee", dataList.size.toString())
        dataList.forEach {
            baseCrudOperations.storeSingleEntity(it)
        }
        return Response.Success(Unit)
    }

    override suspend fun syncData(
        context: Context,
        idsString: String,
        filterPropertyName: String?,
        filterValue: Any?
    ): Response<List<T>> {
        val currentEntities = getEntitiesLocally(filterPropertyName, filterValue).data ?: listOf()
        val idsArray = ListUtil.joinedIdStringToArray(idsString)
        val entitiesIds = currentEntities.map { it.id.toString() }
        val missingIds = idsArray.minus(entitiesIds.toSet())
        Log.d("CurrentEntities", currentEntities.size.toString())
        Log.d("idsArray", idsArray.size.toString())
        Log.d("filterValue", filterValue.toString())
        if (currentEntities.size != idsArray.size || missingIds.isNotEmpty()) {
            val targetIds = if (currentEntities.size < idsArray.size) {
                missingIds.joinToString("@")
            } else {
                val outdatedIds = entitiesIds.minus(idsArray.toSet())
                outdatedIds.forEach {
                    baseCrudOperations.removeRelationWithParent(
                        it.toInt(),
                        filterValue.toString().toInt()
                    )
                }
                idsArray.joinToString("@")
            }
            var missingEntities = listOf<T>()
            val missingData = getDataFromInternet(context, targetIds)
            missingData.data?.let { data ->
                storeData(data)
                missingEntities = data.map { it.toEntity() as T }
            }
            return when (missingEntities.size) {
                0 -> {
                    Response.Error(Response.NO_INTERNET, currentEntities)
                }
                else -> {
                    if (currentEntities.size < idsArray.size) {
                        Response.Success(currentEntities + (missingEntities))
                    } else {
                        Response.Success(missingEntities)
                    }
                }
            }
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
