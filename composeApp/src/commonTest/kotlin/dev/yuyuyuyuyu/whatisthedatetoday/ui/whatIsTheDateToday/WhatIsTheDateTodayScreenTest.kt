package dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WhatIsTheDateTodayScreenTest {

    @OptIn(ExperimentalTestApi::class, kotlin.js.ExperimentalWasmJsInterop::class)
    @Test
    fun doesNotCrashWhenDisplayed() =
        kotlinx.coroutines.test.runTest {
            if (dev.yuyuyuyuyu.whatisthedatetoday.getPlatform().name == "Web with Kotlin/JS")
                return@runTest
            runComposeUiTest {
                setContent {
                    WhatIsTheDateTodayScreen(
                        viewModel =
                            object : WhatIsTheDateTodayViewModel {
                                override val uiState: StateFlow<WhatIsTheDateTodayUiState> =
                                    MutableStateFlow(
                                        WhatIsTheDateTodayUiState(
                                            japaneseEra = "令和",
                                            year = 2026,
                                            japaneseYear = 8,
                                            month = 5,
                                            day = 5,
                                            dayOfWeek = "火",
                                        )
                                    )
                            }
                    )
                }
            }
        }
}
