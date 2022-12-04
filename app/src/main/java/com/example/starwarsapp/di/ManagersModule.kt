package com.example.starwarsapp.di

import com.example.starwarsapp.StarWarsApplication
import com.example.starwarsapp.data.local.db.StarWarsDB
import com.example.starwarsapp.data.local.interfaces.*
import com.example.starwarsapp.data.local.managers.*
import com.example.starwarsapp.data.remote.api.Swapi
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.remote.managers.SwapiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagersModule {
    @Singleton
    @Provides
    fun provideStarWarsDB(): StarWarsDB {
        return StarWarsApplication.starWarsDB as StarWarsDB
    }

    @Singleton
    @Provides
    fun provideSwapiRepository(retrofitService: Swapi): SwapiRepository {
        return SwapiManager(retrofitService)
    }

    // MOVIES
    @Singleton
    @Provides
    fun provideMovieLocalRepository(): MovieLocalRepository {
        return MovieLocalManager(StarWarsApplication.starWarsDB as StarWarsDB)
    }

    @Singleton
    @Provides
    fun provideMovieDataRepository(swapi: SwapiRepository, movieLocalRepository: MovieLocalRepository): MovieDataRepository {
        return MovieDataManager(swapi, movieLocalRepository)
    }

    // CHARACTERS
    @Singleton
    @Provides
    fun provideCharacterLocalRepository(): CharacterLocalRepository {
        return CharacterLocalManager(StarWarsApplication.starWarsDB as StarWarsDB)
    }

    @Singleton
    @Provides
    fun provideCharacterDataRepository(swapi: SwapiRepository, characterLocalRepository: CharacterLocalRepository): CharacterDataRepository {
        return CharacterDataManager(swapi, characterLocalRepository)
    }

    // PLANETS
    @Singleton
    @Provides
    fun providePlanetLocalRepository(): PlanetLocalRepository {
        return PlanetLocalManager(StarWarsApplication.starWarsDB as StarWarsDB)
    }

    @Singleton
    @Provides
    fun providePlanetDataRepository(swapi: SwapiRepository, planetLocalRepository: PlanetLocalRepository): PlanetDataRepository {
        return PlanetDataManager(swapi, planetLocalRepository)
    }
}
