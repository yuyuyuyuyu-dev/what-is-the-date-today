package dev.yuyuyuyuyu.whatisthedatetoday.di

import kotlin.time.Clock

expect fun createAppComponent(clock: Clock): AppComponent
