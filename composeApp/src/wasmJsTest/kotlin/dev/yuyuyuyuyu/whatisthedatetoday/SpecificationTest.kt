package dev.yuyuyuyuyu.whatisthedatetoday

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import dev.yuyuyuyuyu.whatisthedatetoday.support.awaitStringResources
import dev.yuyuyuyuyu.whatisthedatetoday.support.fixBrowserClockAt
import dev.yuyuyuyuyu.whatisthedatetoday.support.restoreBrowser
import kotlinx.datetime.LocalDateTime
import kotlin.test.AfterTest
import kotlin.test.Test

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
            awaitStringResources()

            // Assert
            onNodeWithText("(Reiwa 5) 2023/06/22\nThursday").assertIsDisplayed()
        }
}
