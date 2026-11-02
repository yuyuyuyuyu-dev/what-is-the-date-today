package dev.yuyuyuyuyu.whatisthedatetoday.di

import kotlin.time.Clock

actual fun createAppComponent(clock: Clock): AppComponent = AppComponent::class.create(clock)
