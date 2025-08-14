package dev.yuyuyuyuyu.whatisthedatetoday

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.yuyuyuyuyu.whatisthedatetoday.ui.WhatIsTheDateTodayApp
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        WhatIsTheDateTodayApp()
    }
}