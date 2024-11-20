package com.santimattius.basic.skeleton.di

import com.santimattius.basic.skeleton.core.data.CharacterRepository
import com.santimattius.basic.skeleton.core.data.DragonBallCharacterService
import com.santimattius.basic.skeleton.core.network.RetrofitServiceCreator
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DataModule {

    @Single
    fun provideCharacterServices(serviceCreator: RetrofitServiceCreator): DragonBallCharacterService {
        return serviceCreator.create()
    }

    @Single
    fun provideCharacterRepository(service: DragonBallCharacterService): CharacterRepository {
        return CharacterRepository(service)
    }
}