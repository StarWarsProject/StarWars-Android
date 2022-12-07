package com.example.starwarsapp.data.sync.managers

import com.example.starwarsapp.data.sync.interfaces.CharacterDataRepository
import com.example.starwarsapp.data.local.interfaces.CharacterLocalRepository
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.remote.models.People
import com.example.starwarsapp.data.sync.interfaces.BaseEntityCrud
import com.example.starwarsapp.utils.Response
import javax.inject.Inject

class CharacterCrudOperations
@Inject
constructor(
    private val swapiRepository: SwapiRepository,
    private val characterLocalRepository: CharacterLocalRepository
) : BaseEntityCrud<CharacterEntity> {
    override suspend fun getAllLocal(propName: String?, value: Any?): Response<List<CharacterEntity>> {
        return if (propName == "movie") {
            Response.Success(characterLocalRepository.getCharactersForMovie(value as Int))
        } else {
            Response.Error("Unsupported field")
        }
    }

    override suspend fun getAllRemote(sourceArrayIds: List<String>): Response<List<IBaseRemoteData>> {
        return swapiRepository.getEntitiesForMovie(sourceArrayIds, People::class.java)
    }

    override suspend fun storeSingleEntity(data: IBaseRemoteData): Response<Unit> {
        return Response.Success(characterLocalRepository.storeCharacterForMovie(data as People))
    }
}

class CharacterDataManager
@Inject
constructor(swapiRepository: SwapiRepository, characterLocalRepository: CharacterLocalRepository) : CharacterDataRepository,
    BaseDataManager<CharacterEntity>(CharacterCrudOperations(swapiRepository, characterLocalRepository))
