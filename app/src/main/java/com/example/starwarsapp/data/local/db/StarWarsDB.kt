package com.example.starwarsapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.starwarsapp.data.local.dao.*
import com.example.starwarsapp.data.local.models.*

@Database(entities = [MovieEntity::class, CharacterEntity::class, PlanetEntity::class, SpecieEntity::class, StarshipEntity::class, VehicleEntity::class, MovieCharactersEntity::class, MoviePlanetsEntity::class, MovieSpeciesEntity::class, MovieStarshipsEntity::class, MovieVehiclesEntity::class], version = 1, exportSchema = false)
abstract class StarWarsDB : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun characterDao(): CharacterDao
    abstract fun planetDao(): PlanetDao
    abstract fun specieDao(): SpecieDao
    abstract fun starshipDao(): StarshipDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun movieCharactersDao(): MovieCharactersDao
    abstract fun moviePlanetsDao(): MoviePlanetsDao
    abstract fun movieSpeciesDao(): MovieSpeciesDao
    abstract fun movieStarshipDao(): MovieStarshipsDao
    abstract fun movieVehiclesDao(): MovieVehiclesDao

    companion object {
        @Volatile
        private var INSTANCE: StarWarsDB? = null

        fun create(context: Context): StarWarsDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, StarWarsDB::class.java, "StarWarsDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
