package com.santimattius.basic.skeleton.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.santimattius.basic.skeleton.ui.screens.detail.DetailScreenRoute
import com.santimattius.basic.skeleton.ui.screens.detail.DetailViewModel
import com.santimattius.basic.skeleton.ui.screens.home.HomeScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data class Detail(val id: String) : NavKey

@Composable
fun AppNavGraph() {
    val backStack = rememberNavBackStack(Home)
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Home> {
                HomeScreenRoute {
                    backStack.add(Detail(it.id.toString()))
                }
            }
            entry<Detail> { key ->
                DetailScreenRoute(
                    viewModel = viewModel(factory = DetailViewModel.Factory(key)),
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}