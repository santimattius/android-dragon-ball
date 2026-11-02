package com.santimattius.basic.skeleton.ui.navigation

import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.deeplink.BackStackMatchResult
import androidx.navigation3.runtime.deeplink.DeepLinkMatcher
import androidx.navigation3.runtime.deeplink.DeepLinkRequest
import androidx.navigation3.runtime.deeplink.DeepLinkUri
import androidx.navigation3.runtime.deeplink.UriDeepLinkMatcher
import androidx.navigation3.runtime.deeplink.withBackStack
import kotlinx.serialization.serializer

private val deepLinkMatchers: List<DeepLinkMatcher<*, *>> = listOf(
    UriDeepLinkMatcher(
        uriPattern = DeepLinkUri("dragonball://characters/{id}"),
        serializer = serializer<Detail>()
    ).withBackStack { result ->
        listOf(Home, result.key)
    },
    UriDeepLinkMatcher(
        uriPattern = DeepLinkUri("https://dragonball.santimattius.com/characters/{id}"),
        serializer = serializer<Detail>()
    ).withBackStack { result ->
        listOf(Home, result.key)
    }
)

internal fun resolveDeepLink(request: DeepLinkRequest): List<NavKey>? {
    val matchResult = deepLinkMatchers
        .mapNotNull { matcher -> matcher.match(request) }
        .maxOrNull()
        ?: return null

    return when (matchResult) {
        is BackStackMatchResult<*, *> -> {
            @Suppress("UNCHECKED_CAST")
            matchResult.backStack as List<NavKey>
        }

        else -> listOf(matchResult.key as NavKey)
    }
}
