package com.example.starwarsapp.data.sync.managers

import com.example.starwarsapp.data.local.interfaces.SpecieLocalRepository
import com.example.starwarsapp.data.local.models.SpecieEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.remote.models.Specie
import com.example.starwarsapp.data.sync.interfaces.BaseEntityCrud
import com.example.starwarsapp.data.sync.interfaces.SpecieDataRepository
import com.example.starwarsapp.utils.Response
import javax.inject.Inject

class SpecieCrudOperations
@Inject
constructor(
    private val swapiRepository: SwapiRepository,
    private val specieLocalRepository: SpecieLocalRepository
) : BaseEntityCrud<SpecieEntity> {
    override suspend fun getAllLocal(propName: String?, value: Any?): Response<List<SpecieEntity>> {
        return if (propName == "movie") {
            Response.Success(specieLocalRepository.getSpeciesForMovie(value as Int))
        } else {
            Response.Error("Unsupported field")
        }
    }

    override suspend fun getAllRemote(sourceArrayIds: List<String>): Response<List<IBaseRemoteData>> {
        return swapiRepository.getEntitiesForMovie(sourceArrayIds, Specie::class.java)
    }

    override suspend fun storeSingleEntity(data: IBaseRemoteData): Response<Unit> {
        return Response.Success(specieLocalRepository.storeSpecieForMovie(data as Specie))
    }
}

class SpecieDataManager
@Inject
constructor(swapiRepository: SwapiRepository, specieLocalRepository: SpecieLocalRepository) :
    SpecieDataRepository, BaseDataManager<SpecieEntity>(SpecieCrudOperations(swapiRepository, specieLocalRepository))
