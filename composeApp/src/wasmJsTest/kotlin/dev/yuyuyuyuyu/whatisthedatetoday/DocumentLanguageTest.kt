package dev.yuyuyuyuyu.whatisthedatetoday

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import dev.yuyuyuyuyu.whatisthedatetoday.support.documentLanguage
import dev.yuyuyuyuyu.whatisthedatetoday.support.removeDocumentLanguage
import dev.yuyuyuyuyu.whatisthedatetoday.support.restoreBrowserLanguage
import dev.yuyuyuyuyu.whatisthedatetoday.support.setBrowserLanguage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.awaitPendingResourceStateLoads
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class, ExperimentalResourceApi::class)
class DocumentLanguageTest {
    @AfterTest
    fun tearDown() {
        restoreBrowserLanguage()
        removeDocumentLanguage()
    }

    @Test
    fun `marks the page as Japanese in a Japanese browser`() = assertDocumentLanguage("ja", browserLanguage = "ja")

    @Test
    fun `marks the page as English in an English browser`() = assertDocumentLanguage("en", browserLanguage = "en-US")

    @Test
    fun `falls back to English in other languages`() = assertDocumentLanguage("en", browserLanguage = "fr")

    private fun assertDocumentLanguage(
        expected: String,
        browserLanguage: String,
    ) = runComposeUiTest {
        setBrowserLanguage(browserLanguage)
        setContent { App() }
        awaitPendingResourceStateLoads()
        waitForIdle()

        assertEquals(expected, documentLanguage())
    }
}
