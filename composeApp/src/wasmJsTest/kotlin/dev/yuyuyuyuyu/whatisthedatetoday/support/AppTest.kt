@file:OptIn(ExperimentalTestApi::class, ExperimentalWasmJsInterop::class)

package dev.yuyuyuyuyu.whatisthedatetoday.support

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToString
import androidx.compose.ui.test.v2.runComposeUiTest
import kotlinx.coroutines.await
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny
import kotlin.js.Promise
import kotlin.test.fail

private const val POLL_INTERVAL_MILLISECONDS = 20
private const val MAX_POLLS = 500

fun runAppTest(block: suspend ComposeUiTest.() -> Unit) =
    runComposeUiTest {
        try {
            block()
        } finally {
            restoreBrowser()
        }
    }

suspend fun ComposeUiTest.awaitDisplayed(expected: String) {
    awaitText("is exactly '$expected'") { it == expected }
}

suspend fun ComposeUiTest.awaitDisplayedContaining(vararg candidates: String) {
    awaitText("contains one of ${candidates.toList()}") { text -> candidates.any { it in text } }
}

private suspend fun ComposeUiTest.awaitText(
    description: String,
    predicate: (String) -> Boolean,
) {
    val matcher =
        SemanticsMatcher("text $description") { node ->
            node.config.getOrElse(SemanticsProperties.Text) { emptyList() }.any { predicate(it.text) }
        }
    repeat(MAX_POLLS) {
        waitForIdle()
        if (onAllNodes(matcher).fetchSemanticsNodes().isNotEmpty()) return
        sleep(POLL_INTERVAL_MILLISECONDS).await<JsAny?>()
    }
    fail("No text $description. The screen was:\n${onRoot().printToString()}")
}

@Suppress("UnusedParameter")
private fun sleep(ms: Int): Promise<JsAny?> = js("new Promise((resolve) => setTimeout(resolve, ms))")
