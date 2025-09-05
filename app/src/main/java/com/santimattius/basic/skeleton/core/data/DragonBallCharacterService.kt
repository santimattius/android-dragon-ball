package com.santimattius.basic.skeleton.core.data

import retrofit2.http.GET
import retrofit2.http.Path

interface DragonBallCharacterService {

    @GET("/api/characters")
    suspend fun getCharacters(): DragonBallCharacterResponse

    @GET("/api/characters/{id}")
    suspend fun findCharacterById(@Path("id") id: Long): DragonBallCharacter
}