package com.example.starwarsapp.data.sync.managers

import com.example.starwarsapp.data.sync.interfaces.PlanetDataRepository
import com.example.starwarsapp.data.local.interfaces.PlanetLocalRepository
import com.example.starwarsapp.data.local.models.PlanetEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.remote.models.Planet
import com.example.starwarsapp.data.sync.interfaces.BaseEntityCrud
import com.example.starwarsapp.utils.Response
import javax.inject.Inject

class PlanetCrudOperations
@Inject
constructor(
    private val swapiRepository: SwapiRepository,
    private val planetLocalRepository: PlanetLocalRepository
) : BaseEntityCrud<PlanetEntity> {
    override suspend fun getAllLocal(propName: String?, value: Any?): Response<List<PlanetEntity>> {
        return if (propName == "movie") {
            Response.Success(planetLocalRepository.getPlanetsForMovie(value as Int))
        } else {
            Response.Error("Unsupported field")
        }
    }

    override suspend fun getAllRemote(sourceArrayIds: List<String>): Response<List<IBaseRemoteData>> {
        return swapiRepository.getEntitiesForMovie(sourceArrayIds, Planet::class.java)
    }

    override suspend fun storeSingleEntity(data: PlanetEntity, parentId: Int?): Response<Unit> {
        return if (parentId != null) {
            Response.Success(planetLocalRepository.storePlanetForMovie(data, parentId))
        } else {
            Response.Error("No parent ID")
        }
    }
}

class PlanetDataManager
@Inject
constructor(swapiRepository: SwapiRepository, planetLocalRepository: PlanetLocalRepository) :
    PlanetDataRepository, BaseDataManager<PlanetEntity>(PlanetCrudOperations(swapiRepository, planetLocalRepository))
