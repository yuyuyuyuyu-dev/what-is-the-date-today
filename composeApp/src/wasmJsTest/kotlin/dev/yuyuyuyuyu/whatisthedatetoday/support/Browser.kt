@file:OptIn(ExperimentalWasmJsInterop::class)
@file:Suppress("UnusedParameter")

package dev.yuyuyuyuyu.whatisthedatetoday.support

import kotlin.js.ExperimentalWasmJsInterop

fun setBrowserLanguage(language: String): Unit =
    js(
        """{
        Object.defineProperty(navigator, 'languages', { configurable: true, get: () => [language] });
        Object.defineProperty(navigator, 'language', { configurable: true, get: () => language });
        window.dispatchEvent(new Event('languagechange'));
    }""",
    )

fun browserToday(): String =
    js(
        """(() => {
        const today = new Date();
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const day = String(today.getDate()).padStart(2, '0');
        return today.getFullYear() + '/' + month + '/' + day;
    })()""",
    )

fun restoreBrowserLanguage(): Unit =
    js(
        """{
        delete navigator.languages;
        delete navigator.language;
        window.dispatchEvent(new Event('languagechange'));
    }""",
    )
