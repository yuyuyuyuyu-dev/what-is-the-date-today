package dev.yuyuyuyuyu.whatisthedatetoday.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import dev.yuyuyuyuyu.whatisthedatetoday.di.AppComponent
import dev.yuyuyuyuyu.whatisthedatetoday.ui.openSourceLicenses.OpenSourceLicensesScreen
import dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday.WhatIsTheDateTodayScreen

@Composable
fun MainNavigation(
    backStack: MutableList<MainNavigationRoute>,
    appComponent: AppComponent,
    modifier: Modifier = Modifier,
) {
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                MainNavigationRoute.WhatIsTheDateToday -> {
                    NavEntry(key) {
                        WhatIsTheDateTodayScreen(
                            viewModel = appComponent.whatIsTheDateTodayViewModel
                        )
                    }
                }
                MainNavigationRoute.OpenSourceLicenses -> {
                    NavEntry(key) { OpenSourceLicensesScreen() }
                }
            }
        },
    )
}
