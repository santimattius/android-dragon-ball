package com.santimattius.basic.skeleton.di

import com.santimattius.basic.skeleton.core.data.CharacterRepository
import com.santimattius.basic.skeleton.core.data.DragonBallCharacterService
import com.santimattius.basic.skeleton.core.network.RetrofitServiceCreator
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.santimattius.basic.skeleton.ui")
class DiModule {

    @Single(createdAtStart = true)
    fun provideRetrofitCreator(): RetrofitServiceCreator {
        return RetrofitServiceCreator(baseUrl = "https://dragonball-api.com")
    }

    @Single(createdAtStart = true)
    fun provideCharacterServices(serviceCreator: RetrofitServiceCreator): DragonBallCharacterService {
        return serviceCreator.create()
    }

    @Single
    fun provideCharacterRepository(service: DragonBallCharacterService): CharacterRepository {
        return CharacterRepository(service)
    }
}
