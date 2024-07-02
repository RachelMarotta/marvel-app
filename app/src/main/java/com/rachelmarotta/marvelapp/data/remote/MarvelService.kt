package com.rachelmarotta.marvelapp.data.remote

import com.rachelmarotta.marvelapp.data.model.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("ts") timestamp: String,
        @Query("apikey") publicKey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<CharactersResponse>

    @GET("v1/public/characters")
    suspend fun searchCharactersByName(
        @Query("ts") timestamp: String,
        @Query("apikey") publicKey: String,
        @Query("hash") hash: String,
        @Query("nameStartsWith") name: String
    ): Response<CharactersResponse>
}

