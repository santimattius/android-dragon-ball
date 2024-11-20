package com.santimattius.basic.skeleton.core.data

class CharacterRepository(
    private val service: DragonBallCharacterService
) {

    suspend fun getCharacters(): Result<List<Character>> = runCatching {
        val response = service.getCharacters()
        response.items
    }
}