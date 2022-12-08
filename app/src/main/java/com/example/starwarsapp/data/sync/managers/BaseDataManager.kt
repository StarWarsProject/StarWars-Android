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
        return when (val response = baseCrudOperations.getAllLocal(byPropertyName, value)) {
            is Response.Error -> {
                val localData = response.data!!
                if (localData.isEmpty()) {
                    Response.Error(Response.NO_DATA_AVAILABLE)
                } else {
                    Response.Success(localData)
                }
            }
            is Response.Success -> {
                Response.Error(Response.DATABASE_ERROR)
            }
        }
    }

    override suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<T>> {
        Log.d("baseMan", "from internet")
        return if (Utils.checkForInternet(context)) {
            val dataIdsList = ListUtil.joinedIdStringToArray(sourceStringIds)
            when (val remoteDataResponse = baseCrudOperations.getAllRemote(dataIdsList)) {
                is Response.Error -> {
                    return Response.Error(Response.NO_DATA_AVAILABLE)
                }
                is Response.Success -> {
                    val data = remoteDataResponse.data!!.map {
                        it.toEntity() as T
                    }
                    return Response.Success(data)
                }
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
            when (val missingEntities = getDataFromInternet(context, targetIds)) {
                is Response.Error -> {
                    return Response.Error(Response.NO_INTERNET, currentEntities)
                }
                is Response.Success -> {
                    val data = missingEntities.data!!
                    if (filterValue != null) {
                        storeData(data, filterValue as Int)
                    }
                    return if (currentEntities.size < currentEntitiesIds.count()) {
                        Response.Success(currentEntities + (missingEntities.data))
                    } else {
                        Response.Success(missingEntities.data)
                    }
                }
            }
        } else {
            return Response.Success(currentEntities)
        }
    }
}
