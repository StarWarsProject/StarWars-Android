package com.example.starwarsapp.data.local.models

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "vehicleId"])
data class MovieVehiclesEntity(
    val movieId: Int,
    val vehicleId: Int
)
