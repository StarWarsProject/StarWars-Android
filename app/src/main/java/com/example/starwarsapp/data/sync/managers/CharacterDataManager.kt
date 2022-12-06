package com.example.starwarsapp.data.sync.managers

import com.example.starwarsapp.data.sync.interfaces.CharacterDataRepository
import com.example.starwarsapp.data.local.interfaces.CharacterLocalRepository
import com.example.starwarsapp.data.local.models.CharacterEntity
import com.example.starwarsapp.data.remote.interfaces.IBaseRemoteData
import com.example.starwarsapp.data.remote.interfaces.SwapiRepository
import com.example.starwarsapp.data.sync.interfaces.BaseEntityCrud
import com.example.starwarsapp.utils.Response
import javax.inject.Inject

class CharacterCrudOperations
@Inject
constructor(
    private val swapiRepository: SwapiRepository,
    private val characterLocalRepository: CharacterLocalRepository
) : BaseEntityCrud<CharacterEntity> {
    override fun getAllLocal(propName: String?, value: Any?): Response<List<CharacterEntity>> {
        return if (propName == "movie") {
            Response.Success(characterLocalRepository.getCharactersForMovie(value as Int))
        } else {
            Response.Error("Unsupported field")
        }
    }

    override suspend fun getAllRemote(sourceArrayIds: List<String>): Response<List<IBaseRemoteData>> {
        return swapiRepository.getCharactersForMovie(sourceArrayIds)
    }

    override fun storeSingleEntity(data: CharacterEntity, parentId: Int?): Response<Unit> {
        return if (parentId != null) {
            Response.Success(characterLocalRepository.storeCharacterForMovie(data, parentId))
        } else {
            Response.Error("No parent ID")
        }
    }
}

class CharacterDataManager
@Inject
constructor(swapiRepository: SwapiRepository, characterLocalRepository: CharacterLocalRepository) : CharacterDataRepository,
    BaseDataManager<CharacterEntity>(CharacterCrudOperations(swapiRepository, characterLocalRepository))
