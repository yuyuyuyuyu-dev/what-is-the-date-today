package dev.yuyuyuyuyu.whatisthedatetoday.specification

import dev.yuyuyuyuyu.whatisthedatetoday.App
import dev.yuyuyuyuyu.whatisthedatetoday.support.awaitDisplayed
import dev.yuyuyuyuyu.whatisthedatetoday.support.fixBrowserClockAt
import dev.yuyuyuyuyu.whatisthedatetoday.support.runAppTest
import dev.yuyuyuyuyu.whatisthedatetoday.support.setBrowserLanguage
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test

class DateDisplaySpecificationTest {
    @Test
    fun shouldShowTheDateInJapaneseWhenTheBrowserLanguageIsJapanese() =
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
    fun shouldShowTheDateInEnglishWhenTheBrowserLanguageIsEnglish() =
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
