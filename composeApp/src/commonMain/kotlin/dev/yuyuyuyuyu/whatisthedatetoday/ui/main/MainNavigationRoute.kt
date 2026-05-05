package dev.yuyuyuyuyu.whatisthedatetoday.ui.main

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainNavigationRoute : NavKey {
    @Serializable
    data object WhatIsTheDateToday : MainNavigationRoute

    @Serializable
    data object OpenSourceLicenses : MainNavigationRoute
}
