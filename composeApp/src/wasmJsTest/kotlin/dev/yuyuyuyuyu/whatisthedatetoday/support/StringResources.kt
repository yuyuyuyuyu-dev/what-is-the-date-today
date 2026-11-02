@file:OptIn(ExperimentalTestApi::class, ExperimentalWasmJsInterop::class)

package dev.yuyuyuyuyu.whatisthedatetoday.support

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToString
import kotlinx.coroutines.await
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny
import kotlin.js.Promise
import kotlin.test.fail

private const val POLL_INTERVAL_MILLISECONDS = 20
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
        sleep(POLL_INTERVAL_MILLISECONDS).await<JsAny?>()
    }
    fail("String resources did not finish loading. The screen was:\n${onRoot().printToString()}")
}

private fun ComposeUiTest.displayedTexts(): List<String> =
    onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsProperties.Text))
        .fetchSemanticsNodes()
        .map { node -> node.config[SemanticsProperties.Text].joinToString { it.text } }

@Suppress("UnusedParameter")
private fun sleep(ms: Int): Promise<JsAny?> = js("new Promise((resolve) => setTimeout(resolve, ms))")
