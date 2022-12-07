package com.example.starwarsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.starwarsapp.data.local.models.SpecieEntity

@Dao
interface SpecieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSpecie(specieEntity: SpecieEntity)
}
