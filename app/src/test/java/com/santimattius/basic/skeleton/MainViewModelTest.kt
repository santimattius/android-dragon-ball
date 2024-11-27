package com.santimattius.basic.skeleton


import androidx.lifecycle.viewmodel.testing.viewModelScenario
import app.cash.turbine.test
import com.santimattius.basic.skeleton.core.data.CharacterRepository
import com.santimattius.basic.skeleton.core.data.DragonBallCharacterService
import com.santimattius.basic.skeleton.di.AppModule
import com.santimattius.basic.skeleton.di.DataModule
import com.santimattius.basic.skeleton.tools.rules.MainCoroutinesTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.get
import org.koin.dsl.module
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

class MainViewModelTest : KoinTest {

    @get:Rule
    val mainCoroutinesTestRule = MainCoroutinesTestRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(AppModule().module + DataModule().module)
        modules(
            modules = module {
                single<DragonBallCharacterService> { MockDragonBallCharacterService() }
            }
        )
    }

    @Test
    fun testViewModelScenario() {
        viewModelScenario {
            MainViewModel(get<CharacterRepository>())
        }.use { scenario ->
            val viewModel = scenario.viewModel
            runTest {
                viewModel.state.test {
                    assertEquals(10, awaitItem().characters.size)
                    cancelAndIgnoreRemainingEvents()
                }
            }
        }
    }
}