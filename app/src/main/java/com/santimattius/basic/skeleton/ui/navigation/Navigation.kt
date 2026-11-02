package com.santimattius.basic.skeleton.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.deeplink.DeepLinkRequest
import androidx.navigation3.ui.NavDisplay
import com.santimattius.basic.skeleton.ui.screens.detail.DetailScreenRoute
import com.santimattius.basic.skeleton.ui.screens.detail.DetailViewModel
import com.santimattius.basic.skeleton.ui.screens.home.HomeScreenRoute
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data object Home : NavKey

@Serializable
data class Detail(val id: String) : NavKey

@Composable
fun AppNavGraph(deepLinkIntent: Intent? = null) {
    val backStack = rememberNavBackStack(Home)

    LaunchedEffect(deepLinkIntent) {
        val uri = deepLinkIntent?.data ?: return@LaunchedEffect
        val deepLinkBackStack = resolveDeepLink(DeepLinkRequest(uri = uri))
            ?: return@LaunchedEffect

        backStack.clear()
        backStack.addAll(deepLinkBackStack)
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
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
                    viewModel = koinViewModel<DetailViewModel> { parametersOf(key.id) },
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}