package com.santimattius.basic.skeleton


import android.app.Application
import com.santimattius.basic.skeleton.di.DiModule
import org.koin.android.ext.koin.androidContext
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration
import org.koin.plugin.module.dsl.koinConfiguration

@KoinApplication(modules = [DiModule::class])
class DragonBallApplication

@OptIn(KoinExperimentalAPI::class)
class MainApplication : Application(), KoinStartup {

    override fun onKoinStartup(): KoinConfiguration = koinConfiguration<DragonBallApplication> {
        androidContext(this@MainApplication)
        strictOverride()
    }
}
