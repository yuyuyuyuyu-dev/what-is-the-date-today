package dev.yuyuyuyuyu.whatisthedatetoday

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import dev.yuyuyuyuyu.whatisthedatetoday.support.awaitStringResources
import dev.yuyuyuyuyu.whatisthedatetoday.support.fixBrowserClockAt
import dev.yuyuyuyuyu.whatisthedatetoday.support.restoreBrowser
import dev.yuyuyuyuyu.whatisthedatetoday.support.setBrowserLanguage
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
    fun `should show the date in Japanese when the browser language is Japanese`() =
        runComposeUiTest {
            // Arrange
            fixBrowserClockAt(LocalDateTime(2023, 6, 22, 0, 0))
            setBrowserLanguage("ja")

            // Act
            setContent { App() }
            awaitStringResources()

            // Assert
            onNodeWithText("（令和5年）2023年06月22日\n木曜日").assertIsDisplayed()
        }

    @Test
    fun `should show the date in English when the browser language is English`() =
        runComposeUiTest {
            // Arrange
            fixBrowserClockAt(LocalDateTime(2023, 6, 22, 0, 0))
            setBrowserLanguage("en-US")

            // Act
            setContent { App() }
            awaitStringResources()

            // Assert
            onNodeWithText("(Reiwa 5) 2023/06/22\nThursday").assertIsDisplayed()
        }
}
