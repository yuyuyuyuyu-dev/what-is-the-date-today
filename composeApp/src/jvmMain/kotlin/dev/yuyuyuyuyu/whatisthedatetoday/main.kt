package dev.yuyuyuyuyu.whatisthedatetoday

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.yuyuyuyuyu.whatisthedatetoday.ui.WhatIsTheDateTodayApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "WhatIsTheDateToday",
    ) {
        WhatIsTheDateTodayApp()
    }
}