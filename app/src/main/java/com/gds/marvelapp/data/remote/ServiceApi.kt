package com.gds.marvelapp.data.remote

import com.gds.marvelapp.data.model.character.CharacterModelResponse
import com.gds.marvelapp.data.model.comics.ComicModelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("characters")
    suspend fun list(
        @Query("nameStartsWith") nameStartsWith : String? = null
    ) : Response<CharacterModelResponse>

    @GET("characters/{characterId}/comics")
    suspend fun getComics(
        @Path(
            value = "characterId",
            encoded = true
        )characterId : Int
    ) : Response<ComicModelResponse>
}