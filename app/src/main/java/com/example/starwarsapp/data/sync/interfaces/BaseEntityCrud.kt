package com.example.starwarsapp.data.sync.interfaces

import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.utils.Response

interface BaseEntityCrud<T> {
    fun getAllLocal(propName: String?, value: Any?): Response<List<T>>
    suspend fun getAllRemote(sourceArrayIds: List<String>): Response<List<IBaseRemoteData>>
    fun storeSingleEntity(data: T, parentId: Int?): Response<Unit>
}
