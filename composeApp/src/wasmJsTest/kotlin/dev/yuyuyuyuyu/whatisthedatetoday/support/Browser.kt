@file:OptIn(ExperimentalWasmJsInterop::class)
@file:Suppress("UnusedParameter")

package dev.yuyuyuyuyu.whatisthedatetoday.support

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny

private const val NANOSECONDS_PER_MILLISECOND = 1_000_000

private val realDate: JsAny = currentDateClass()

fun fixBrowserClockAt(localDateTime: LocalDateTime) {
    installFixedDate(
        real = realDate,
        year = localDateTime.year,
        monthIndex = localDateTime.month.number - 1,
        day = localDateTime.day,
        hour = localDateTime.hour,
        minute = localDateTime.minute,
        second = localDateTime.second,
        millisecond = localDateTime.nanosecond / NANOSECONDS_PER_MILLISECOND,
    )
}

fun setBrowserLanguage(language: String): Unit =
    js(
        """{
        Object.defineProperty(navigator, 'languages', { configurable: true, get: () => [language] });
        Object.defineProperty(navigator, 'language', { configurable: true, get: () => language });
        window.dispatchEvent(new Event('languagechange'));
    }""",
    )

fun restoreBrowser() {
    restoreDate(realDate)
    restoreNavigatorLanguage()
}

fun browserToday(): String =
    js(
        """(() => {
        const today = new Date();
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const day = String(today.getDate()).padStart(2, '0');
        return today.getFullYear() + '/' + month + '/' + day;
    })()""",
    )

private fun currentDateClass(): JsAny = js("Date")

@Suppress("LongParameterList")
private fun installFixedDate(
    real: JsAny,
    year: Int,
    monthIndex: Int,
    day: Int,
    hour: Int,
    minute: Int,
    second: Int,
    millisecond: Int,
): Unit =
    js(
        """{
        const fixedTime = new real(year, monthIndex, day, hour, minute, second, millisecond).getTime();
        globalThis.Date = class extends real {
            constructor(...args) {
                super(...(args.length === 0 ? [fixedTime] : args));
            }
            static now() {
                return fixedTime;
            }
        };
    }""",
    )

private fun restoreDate(real: JsAny): Unit = js("{ globalThis.Date = real; }")

private fun restoreNavigatorLanguage(): Unit =
    js(
        """{
        delete navigator.languages;
        delete navigator.language;
        window.dispatchEvent(new Event('languagechange'));
    }""",
    )
