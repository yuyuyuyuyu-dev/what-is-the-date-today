package dev.yuyuyuyuyu.whatisthedatetoday

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.awaitPendingResourceStateLoads
import kotlin.test.Test
import kotlin.time.Clock

@OptIn(ExperimentalTestApi::class, ExperimentalResourceApi::class)
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
            awaitPendingResourceStateLoads()

            // Assert
            onNodeWithText("(Reiwa 5) 2023/06/22\nThursday").assertIsDisplayed()
        }
}
