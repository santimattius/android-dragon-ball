package com.santimattius.basic.skeleton

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.santimattius.basic.skeleton.core.data.DragonBallCharacterResponse
import com.santimattius.basic.skeleton.core.data.DragonBallCharacterService
import com.santimattius.basic.skeleton.tools.helpers.JsonLoader

class MockDragonBallCharacterService : DragonBallCharacterService {
    private val jsonLoader = JsonLoader()
    private val gson: Gson = GsonBuilder().create()

    override suspend fun getCharacters(): DragonBallCharacterResponse {
        val json = jsonLoader.load("dragon-ball-response.json")
        return gson.fromJson(
            json,
            DragonBallCharacterResponse::class.java
        )
    }
}