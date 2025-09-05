package com.santimattius.basic.skeleton.core.data

import kotlin.coroutines.cancellation.CancellationException

class CharacterRepository(
    private val service: DragonBallCharacterService
) {

    suspend fun getCharacters(): Result<List<Character>> = runCatchingNoCancellable {
        val response = service.getCharacters()
        response.items
    }

    suspend fun findCharacterById(id: Long): Result<Character> = runCatchingNoCancellable {
        service.findCharacterById(id)
    }
}


inline fun <T, R> T.runCatchingNoCancellable(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (ex: CancellationException) {
        throw ex
    } catch (e: Throwable) {
        Result.failure(e)
    }
}

