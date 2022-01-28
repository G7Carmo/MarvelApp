package com.gds.marvelapp.repository

import com.gds.marvelapp.data.local.MarvelDao
import com.gds.marvelapp.data.model.character.CharacterModel
import com.gds.marvelapp.data.remote.ServiceApi
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val api: ServiceApi,
    private val dao : MarvelDao
) {
    //remote
    suspend fun list(nameStartsWith: String? = null) = api.list(nameStartsWith)
    suspend fun getComics(characterID: Int)= api.getComics(characterID)
    //local
    suspend fun insert(characterModel: CharacterModel) = dao.insert(characterModel)
    suspend fun delete(characterModel: CharacterModel) = dao.delete(characterModel)
    fun getAll() = dao.getAll()
}