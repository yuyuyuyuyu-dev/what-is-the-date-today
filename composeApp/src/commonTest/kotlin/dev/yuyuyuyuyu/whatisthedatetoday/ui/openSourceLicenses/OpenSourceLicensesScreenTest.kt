package dev.yuyuyuyuyu.whatisthedatetoday.ui.openSourceLicenses

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

class OpenSourceLicensesScreenTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun doesNotCrashWhenDisplayed() =
        kotlinx.coroutines.test.runTest {
            if (dev.yuyuyuyuyu.whatisthedatetoday.getPlatform().name == "Web with Kotlin/JS")
                return@runTest
            runComposeUiTest { setContent { OpenSourceLicensesScreen() } }
        }
}
