package dev.yuyuyuyuyu.whatisthedatetoday.ui.openSourceLicenses

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

class OpenSourceLicensesScreenTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun doesNotCrashWhenDisplayed() =
        kotlinx.coroutines.test.runTest {
            val isAndroidOrJs =
                try {
                    val name = dev.yuyuyuyuyu.whatisthedatetoday.getPlatform().name
                    name.startsWith("Android") || name == "Web with Kotlin/JS"
                } catch (e: Throwable) {
                    true
                }
            if (isAndroidOrJs) {
                return@runTest
            }
            runComposeUiTest { setContent { OpenSourceLicensesScreen() } }
        }
}
