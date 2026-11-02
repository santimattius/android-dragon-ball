package com.santimattius.basic.skeleton.ui.navigation

import androidx.navigation3.runtime.deeplink.DeepLinkRequest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DeepLinkNavigationTest {

    @Test
    fun customSchemeOpensCharacterDetailWithHomeAsParent() {
        val backStack = resolveDeepLink(
            DeepLinkRequest(uri = "dragonball://characters/42")
        )

        assertEquals(listOf(Home, Detail("42")), backStack)
    }

    @Test
    fun httpsAppLinkOpensCharacterDetailWithHomeAsParent() {
        val backStack = resolveDeepLink(
            DeepLinkRequest(uri = "https://dragonball.santimattius.com/characters/42")
        )

        assertEquals(listOf(Home, Detail("42")), backStack)
    }

    @Test
    fun unrelatedUriDoesNotReplaceNavigationStack() {
        val backStack = resolveDeepLink(
            DeepLinkRequest(uri = "https://example.com/characters/42")
        )

        assertNull(backStack)
    }
}
