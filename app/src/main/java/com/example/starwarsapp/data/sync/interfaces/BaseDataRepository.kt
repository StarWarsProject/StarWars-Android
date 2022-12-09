package com.example.starwarsapp.data.sync.interfaces

import android.content.Context
import com.example.starwarsapp.data.local.models.BaseEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.utils.Response

interface BaseDataRepository<T : BaseEntity> {
    suspend fun getEntitiesLocally(byPropertyName: String?, value: Any?): Response<List<T>>
    suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<IBaseRemoteData>>
    suspend fun storeData(dataList: List<IBaseRemoteData>): Response<Unit>
    suspend fun syncData(context: Context, idsString: String, filterPropertyName: String?, filterValue: Any?): Response<List<T>>
    suspend fun refreshData(context: Context, idsString: String): Response<List<T>>
}
