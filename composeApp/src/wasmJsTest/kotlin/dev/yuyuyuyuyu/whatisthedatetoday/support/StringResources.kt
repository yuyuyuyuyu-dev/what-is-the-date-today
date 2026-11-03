@file:OptIn(ExperimentalTestApi::class)

package dev.yuyuyuyuyu.whatisthedatetoday.support

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.test.fail
import kotlin.time.Duration.Companion.milliseconds

private val POLL_INTERVAL = 20.milliseconds
private const val MAX_POLLS = 500
private const val POLLS_TO_SETTLE = 3

suspend fun ComposeUiTest.awaitStringResources() {
    var previousTexts = emptyList<String>()
    var settledPolls = 0
    repeat(MAX_POLLS) {
        waitForIdle()
        val texts = displayedTexts()
        val isLoaded = texts.isNotEmpty() && texts.none(String::isEmpty)
        settledPolls = if (isLoaded && texts == previousTexts) settledPolls + 1 else 0
        if (settledPolls == POLLS_TO_SETTLE) return
        previousTexts = texts
        withContext(Dispatchers.Default) { delay(POLL_INTERVAL) }
    }
    fail("String resources did not finish loading. The screen was:\n${onRoot().printToString()}")
}

private fun ComposeUiTest.displayedTexts(): List<String> =
    onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsProperties.Text))
        .fetchSemanticsNodes()
        .map { node -> node.config[SemanticsProperties.Text].joinToString { it.text } }
