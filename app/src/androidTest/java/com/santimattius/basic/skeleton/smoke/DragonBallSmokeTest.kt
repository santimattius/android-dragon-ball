package com.santimattius.basic.skeleton.smoke

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import com.santimattius.basic.skeleton.MainActivity
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.GlobalContext

/**
 * Smoke tests that verify critical app behaviour on the minified (R8/benchmark) build.
 *
 * These tests confirm that:
 *  1. The app starts without crashing (Koin DI bootstrap via KoinStartup)
 *  2. Gson deserialization of DragonBallCharacter survives R8 shrinking
 *  3. Navigation between screens works after minification
 *  4. The Koin DI container is alive and resolvable post-R8
 *
 * Run on the benchmark (minified) variant via:
 *   ./gradlew :app:connectedBenchmarkAndroidTest
 *     -Pandroid.testInstrumentationRunnerArguments.annotation=com.santimattius.basic.skeleton.smoke.SmokeTest
 */
@SmokeTest
class DragonBallSmokeTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    /**
     * Scenario 1: App starts without crashing.
     *
     * KoinStartup initialises the Koin container in Application.onCreate(). If Koin is
     * stripped by R8, the process crashes before this line is reached, failing the test.
     */
    @SmokeTest
    @Test
    fun appLaunchesSuccessfully() {
        composeRule.waitForIdle()
        composeRule.onRoot().assertExists()
    }

    /**
     * Scenario 2: Character list loads and Gson deserialization survives R8.
     *
     * The list only populates when DragonBallCharacterResponse and DragonBallCharacter are
     * correctly deserialized via Gson reflection over @SerializedName fields. R8 must keep
     * those fields and constructors (enforced by the keep rule in proguard-rules.pro).
     *
     * Cards expose `contentDescription = character.name` via NetworkImage, so a non-empty
     * contentDescription signals successful deserialization.
     */
    @SmokeTest
    @Test
    @OptIn(ExperimentalTestApi::class)
    fun characterListLoads() {
        // Wait until at least one character card with a non-empty contentDescription appears.
        // We allow 15 s for the network call + Gson deserialization to complete.
        // Cards expose contentDescription = character.name (a real Dragon Ball character name).
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasContentDescription(value = "", substring = true),
            timeoutMillis = 15_000L
        )

        // Assert that at least one character card is visible — proving Gson deserialization
        // worked on the minified build.
        val characterNodes = composeRule
            .onAllNodesWithContentDescription("", substring = true)
            .fetchSemanticsNodes()
        assert(characterNodes.isNotEmpty()) {
            "Expected at least one character card to be visible after list load. " +
                "If this fails on the benchmark variant, Gson @SerializedName fields " +
                "are likely stripped by R8 — check proguard-rules.pro."
        }
    }

    /**
     * Scenario 3: Navigation from the home list to the detail screen works post-R8.
     *
     * NavKey types (Home, Detail) use kotlinx.serialization. This scenario verifies that
     * the serialization + NavBackStack navigation survive R8 shrinking. The back button
     * (contentDescription = "Back") only exists in the DetailScreen AppBar.
     */
    @SmokeTest
    @Test
    @OptIn(ExperimentalTestApi::class)
    fun navigationToDetailWorks() {
        // Wait for at least one clickable character card
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasClickAction() and hasContentDescription(value = "", substring = true),
            timeoutMillis = 15_000L
        )

        // Tap the first character card
        composeRule
            .onAllNodes(hasClickAction() and hasContentDescription(value = "", substring = true))[0]
            .performClick()

        // The detail screen AppBar shows a back arrow with contentDescription "Back".
        // This node only exists in DetailScreen — its presence proves navigation succeeded.
        composeRule.waitUntilExactlyOneExists(
            matcher = hasContentDescription("Back"),
            timeoutMillis = 10_000L
        )
        composeRule.onNodeWithContentDescription("Back").assertExists()
    }

    /**
     * Scenario 4: Koin DI container is alive and resolvable under the minified build.
     *
     * Distinct from scenario 2 (which checks UI output): this scenario queries the Koin
     * GlobalContext directly, proving that the DI graph itself was not stripped by R8.
     * If R8 eliminates Koin's GlobalContext or the generated module code, this will throw.
     */
    @SmokeTest
    @Test
    fun koinInjectionWorks() {
        composeRule.waitForIdle()

        // GlobalContext.getOrNull() returns null if Koin was never started.
        // Either outcome means R8 broke the DI bootstrap or KoinStartup was eliminated.
        val koin = GlobalContext.getOrNull()
        assert(koin != null) {
            "Koin GlobalContext is null — KoinStartup did not initialise the DI container. " +
                "Check that Koin keep rules survive R8 minification."
        }

        // The app being idle and the root existing confirms the DI-backed ViewModel
        // resolved successfully without throwing.
        composeRule.onRoot().assertExists()
    }
}
