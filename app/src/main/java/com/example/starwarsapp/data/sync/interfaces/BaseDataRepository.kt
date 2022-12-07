package com.example.starwarsapp.data.sync.interfaces

import android.content.Context
import com.example.starwarsapp.utils.Response

interface BaseDataRepository<T> {
    suspend fun getEntitiesLocally(byPropertyName: String?, value: Any?): Response<List<T>>
    suspend fun getDataFromInternet(context: Context, sourceStringIds: String): Response<List<T>>
    suspend fun storeData(dataList: List<T>, parentId: Int? = null): Response<Unit>
    suspend fun syncData(context: Context, idsString: String, filterPropertyName: String?, filterValue: Any?): Response<List<T>>
}
