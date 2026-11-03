package dev.yuyuyuyuyu.whatisthedatetoday

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.test.Test
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeSource

@OptIn(ExperimentalTestApi::class)
class SpecificationTest {
    @Test
    fun `should show the current date`() =
        runComposeUiTest {
            // Arrange
            val clock =
                object : Clock {
                    override fun now() = LocalDateTime(2023, 6, 22, 0, 0).toInstant(TimeZone.currentSystemDefault())
                }

            // Act
            setContent { App(clock) }

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
