package com.santimattius.basic.skeleton.ui.navigation

import android.content.Intent
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.santimattius.basic.skeleton.R
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

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
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

    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()
    val directive = remember(windowAdaptiveInfo) {
        calculatePaneScaffoldDirective(windowAdaptiveInfo)
            .copy(horizontalPartitionSpacerSize = 0.dp)
    }
    val isTwoPane = directive.maxHorizontalPartitions > 1
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>(directive = directive)

    NavDisplay(
        backStack = backStack,
        sceneStrategies = listOf(listDetailStrategy),
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Home>(
                metadata = ListDetailSceneStrategy.listPane(
                    detailPlaceholder = { DetailPlaceholder() }
                )
            ) {
                HomeScreenRoute {
                    val detail = Detail(it.id.toString())
                    // Replace the visible detail instead of stacking one per tap.
                    if (backStack.lastOrNull() is Detail) {
                        backStack[backStack.lastIndex] = detail
                    } else {
                        backStack.add(detail)
                    }
                }
            }
            entry<Detail>(
                metadata = ListDetailSceneStrategy.detailPane()
            ) { key ->
                DetailScreenRoute(
                    viewModel = koinViewModel<DetailViewModel> { parametersOf(key.id) },
                    showBackButton = !isTwoPane,
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}

@Composable
private fun DetailPlaceholder() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(R.string.select_character))
    }
}
