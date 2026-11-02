package dev.yuyuyuyuyu.whatisthedatetoday

import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import dev.yuyuyuyuyu.whatisthedatetoday.support.awaitStringResources
import dev.yuyuyuyuyu.whatisthedatetoday.support.browserToday
import dev.yuyuyuyuyu.whatisthedatetoday.support.fixBrowserClockAt
import dev.yuyuyuyuyu.whatisthedatetoday.support.restoreBrowser
import dev.yuyuyuyuyu.whatisthedatetoday.support.setBrowserLanguage
import kotlinx.datetime.LocalDateTime
import kotlin.test.AfterTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class DateDisplayTest {
    @AfterTest
    fun tearDown() {
        restoreBrowser()
    }

    @Test
    fun `shows every weekday in Japanese`() =
        assertAppShows(
            LocalDateTime(2023, 6, 19, 12, 0) to "（令和5年）2023年06月19日\n月曜日",
            LocalDateTime(2023, 6, 20, 12, 0) to "（令和5年）2023年06月20日\n火曜日",
            LocalDateTime(2023, 6, 21, 12, 0) to "（令和5年）2023年06月21日\n水曜日",
            LocalDateTime(2023, 6, 22, 12, 0) to "（令和5年）2023年06月22日\n木曜日",
            LocalDateTime(2023, 6, 23, 12, 0) to "（令和5年）2023年06月23日\n金曜日",
            LocalDateTime(2023, 6, 24, 12, 0) to "（令和5年）2023年06月24日\n土曜日",
            LocalDateTime(2023, 6, 25, 12, 0) to "（令和5年）2023年06月25日\n日曜日",
            language = "ja",
        )

    @Test
    fun `shows every weekday in English`() =
        assertAppShows(
            LocalDateTime(2023, 6, 19, 12, 0) to "(Reiwa 5) 2023/06/19\nMonday",
            LocalDateTime(2023, 6, 20, 12, 0) to "(Reiwa 5) 2023/06/20\nTuesday",
            LocalDateTime(2023, 6, 21, 12, 0) to "(Reiwa 5) 2023/06/21\nWednesday",
            LocalDateTime(2023, 6, 22, 12, 0) to "(Reiwa 5) 2023/06/22\nThursday",
            LocalDateTime(2023, 6, 23, 12, 0) to "(Reiwa 5) 2023/06/23\nFriday",
            LocalDateTime(2023, 6, 24, 12, 0) to "(Reiwa 5) 2023/06/24\nSaturday",
            LocalDateTime(2023, 6, 25, 12, 0) to "(Reiwa 5) 2023/06/25\nSunday",
        )

    @Test
    fun `pads single digit months and days but not double digit ones`() =
        assertAppShows(
            LocalDateTime(2023, 1, 3, 12, 0) to "(Reiwa 5) 2023/01/03\nTuesday",
            LocalDateTime(2023, 12, 31, 12, 0) to "(Reiwa 5) 2023/12/31\nSunday",
        )

    @Test
    fun `counts Reiwa years from its first day`() =
        assertAppShows(
            LocalDateTime(2019, 5, 1, 12, 0) to "(Reiwa 1) 2019/05/01\nWednesday",
            LocalDateTime(2020, 1, 1, 12, 0) to "(Reiwa 2) 2020/01/01\nWednesday",
        )

    @Test
    fun `changes the date at local midnight`() =
        assertAppShows(
            LocalDateTime(2023, 6, 21, 23, 59, 59, 999_000_000) to "(Reiwa 5) 2023/06/21\nWednesday",
            LocalDateTime(2023, 6, 22, 0, 0) to "(Reiwa 5) 2023/06/22\nThursday",
        )

    @Test
    fun `reads the real clock of the browser`() =
        runComposeUiTest {
            val before = browserToday()

            setContent { App() }
            awaitStringResources()

            onNode(hasText(before, substring = true) or hasText(browserToday(), substring = true)).assertIsDisplayed()
        }

    private fun assertAppShows(
        vararg expectations: Pair<LocalDateTime, String>,
        language: String? = null,
    ) = runComposeUiTest {
        var current by mutableStateOf(expectations.first())
        language?.let(::setBrowserLanguage)
        fixBrowserClockAt(current.first)
        setContent { key(current) { App() } }

        expectations.forEach { expectation ->
            fixBrowserClockAt(expectation.first)
            current = expectation
            awaitStringResources()
            onNodeWithText(expectation.second).assertIsDisplayed()
        }
    }
}
