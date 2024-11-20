package com.santimattius.basic.skeleton.core.data

import retrofit2.http.GET

interface DragonBallCharacterService {

    @GET("/api/characters")
    suspend fun getCharacters(): DragonBallCharacterResponse
}