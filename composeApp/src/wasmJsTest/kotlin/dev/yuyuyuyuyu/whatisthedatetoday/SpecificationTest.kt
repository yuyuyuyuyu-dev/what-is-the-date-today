package dev.yuyuyuyuyu.whatisthedatetoday

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import dev.yuyuyuyuyu.whatisthedatetoday.support.fixBrowserClockAt
import dev.yuyuyuyuyu.whatisthedatetoday.support.restoreBrowser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeSource

@OptIn(ExperimentalTestApi::class)
class SpecificationTest {
    @AfterTest
    fun tearDown() {
        restoreBrowser()
    }

    @Test
    fun `should show the current date`() =
        runComposeUiTest {
            // Arrange
            fixBrowserClockAt(LocalDateTime(2023, 6, 22, 0, 0))

            // Act
            setContent { App() }

            // Assert
            onNodeWithText("(Reiwa 5) 2023/06/22\nThursday").awaitUntilDisplayed()
        }
}

private suspend fun SemanticsNodeInteraction.awaitUntilDisplayed(timeoutMillis: Long = 1_000) {
    val deadline = TimeSource.Monotonic.markNow() + timeoutMillis.milliseconds
    while (true) {
        try {
            assertIsDisplayed()
            return
        } catch (error: AssertionError) {
            if (deadline.hasPassedNow()) throw error
            withContext(Dispatchers.Default) { delay(20.milliseconds) }
        }
    }
}
