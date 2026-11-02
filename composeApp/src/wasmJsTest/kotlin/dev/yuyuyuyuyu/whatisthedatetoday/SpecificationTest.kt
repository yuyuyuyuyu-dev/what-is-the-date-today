package dev.yuyuyuyuyu.whatisthedatetoday

import dev.yuyuyuyuyu.whatisthedatetoday.support.awaitDisplayed
import dev.yuyuyuyuyu.whatisthedatetoday.support.fixBrowserClockAt
import dev.yuyuyuyuyu.whatisthedatetoday.support.runAppTest
import dev.yuyuyuyuyu.whatisthedatetoday.support.setBrowserLanguage
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test

class SpecificationTest {
    @Test
    fun `should show the date in Japanese when the browser language is Japanese`() =
        runAppTest {
            // Arrange
            fixBrowserClockAt(LocalDateTime(2023, 6, 22, 0, 0))
            setBrowserLanguage("ja")

            // Act
            setContent { App() }

            // Assert
            awaitDisplayed("（令和5年）2023年06月22日\n木曜日")
        }

    @Test
    fun `should show the date in English when the browser language is English`() =
        runAppTest {
            // Arrange
            fixBrowserClockAt(LocalDateTime(2023, 6, 22, 0, 0))
            setBrowserLanguage("en-US")

            // Act
            setContent { App() }

            // Assert
            awaitDisplayed("(Reiwa 5) 2023/06/22\nThursday")
        }
}
