package com.example.starwarsapp.data.remote.interfaces

import com.example.starwarsapp.data.local.models.BaseEntity

interface IBaseRemoteData {
    fun toEntity(): BaseEntity
}
