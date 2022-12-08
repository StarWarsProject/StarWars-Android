package com.example.starwarsapp.data.sync.managers

import com.example.starwarsapp.data.local.interfaces.StarshipLocalRepository
import com.example.starwarsapp.data.local.models.StarshipEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.remote.models.Starship
import com.example.starwarsapp.data.sync.interfaces.BaseEntityCrud
import com.example.starwarsapp.data.sync.interfaces.StarshipDataRepository
import com.example.starwarsapp.utils.Response
import javax.inject.Inject

class StarshipCrudOperations
@Inject
constructor(
    private val swapiRepository: SwapiRepository,
    private val starshipLocalRepository: StarshipLocalRepository
) : BaseEntityCrud<StarshipEntity> {
    override suspend fun getAllLocal(propName: String?, value: Any?): Response<List<StarshipEntity>> {
        return if (propName == "movie") {
            Response.Success(starshipLocalRepository.getStarshipsForMovie(value as Int))
        } else {
            Response.Error("Unsupported field")
        }
    }

    override suspend fun getAllRemote(sourceArrayIds: List<String>): Response<List<IBaseRemoteData>> {
        return swapiRepository.getEntitiesForMovie(sourceArrayIds, Starship::class.java)
    }

    override suspend fun storeSingleEntity(data: StarshipEntity, parentId: Int?): Response<Unit> {
        return if (parentId != null) {
            Response.Success(starshipLocalRepository.storeStarshipForMovie(data, parentId))
        } else {
            Response.Error("No parent ID")
        }
    }
}

class StarshipDataManager
@Inject
constructor(swapiRepository: SwapiRepository, starshipLocalRepository: StarshipLocalRepository) :
    StarshipDataRepository, BaseDataManager<StarshipEntity>(StarshipCrudOperations(swapiRepository, starshipLocalRepository))
