package dev.yuyuyuyuyu.whatisthedatetoday.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import dev.yuyuyuyuyu.simpleTopAppBar.SimpleTopAppBar
import dev.yuyuyuyuyu.whatisthedatetoday.di.AppComponent
import org.jetbrains.compose.resources.stringResource
import whatisthedatetoday.composeapp.generated.resources.Res
import whatisthedatetoday.composeapp.generated.resources.app_name
import whatisthedatetoday.composeapp.generated.resources.open_source_licenses

@Composable
fun MainScreen(appComponent: AppComponent) {
    val backStack: MutableList<MainNavigationRoute> =
        rememberSerializable(serializer = SnapshotStateListSerializer()) {
            mutableStateListOf(MainNavigationRoute.WhatIsTheDateToday)
        }

    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title =
                    when (backStack.lastOrNull()) {
                        is MainNavigationRoute.OpenSourceLicenses -> stringResource(Res.string.open_source_licenses)
                        else -> stringResource(Res.string.app_name)
                    },
                navigateBackIsPossible = backStack.size > 1,
                onNavigateBackButtonClick = { backStack.removeLastOrNull() },
                onOpenSourceLicensesButtonClick = {
                    if (backStack.lastOrNull() != MainNavigationRoute.OpenSourceLicenses) {
                        backStack.add(MainNavigationRoute.OpenSourceLicenses)
                    }
                },
                onSourceCodeButtonClick = {
                    uriHandler.openUri("https://github.com/yuyuyuyuyu-dev/what-is-the-date-today")
                },
            )
        },
    ) { innerPadding ->
        MainNavigation(backStack, appComponent, Modifier.padding(innerPadding))
    }
}
