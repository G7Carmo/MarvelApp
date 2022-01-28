package com.gds.marvelapp.repository

import com.gds.marvelapp.data.remote.ServiceApi
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val api: ServiceApi
) {
    //remote
    suspend fun list(nameStartsWith: String? = null) = api.list(nameStartsWith)
    suspend fun getComics(characterID: Int)= api.getComics(characterID)
    //local
}