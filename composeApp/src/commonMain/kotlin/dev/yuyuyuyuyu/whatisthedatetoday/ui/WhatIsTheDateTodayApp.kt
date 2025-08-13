package dev.yuyuyuyuyu.whatisthedatetoday.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.yuyuyuyuyu.mymaterialtheme.MyMaterialTheme
import dev.yuyuyuyuyu.simpleTopAppBar.SimpleTopAppBar
import dev.yuyuyuyuyu.whatisthedatetoday.di.uiModule
import dev.yuyuyuyuyu.whatisthedatetoday.ui.openSourceLicenseList.OpenSourceLicenseListScreen
import dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday.WhatIsTheDateTodayScreen
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import whatisthedatetoday.composeapp.generated.resources.Res
import whatisthedatetoday.composeapp.generated.resources.app_name
import whatisthedatetoday.composeapp.generated.resources.open_source_licenses

@Composable
@Preview
fun WhatIsTheDateTodayApp() {
    val backStack = rememberSaveableBackStack(root = WhatIsTheDateTodayScreen)
    val navigator = rememberCircuitNavigator(backStack) {}
    val currentScreen = backStack.topRecord?.screen

    val uriHandler = LocalUriHandler.current

    KoinApplication(
        application = {
            printLogger()
            modules(uiModule)
        },
    ) {
        MyMaterialTheme {
            Scaffold(
                topBar = {
                    SimpleTopAppBar(
                        title = when (currentScreen) {
                            is OpenSourceLicenseListScreen -> stringResource(Res.string.open_source_licenses)
                            else -> stringResource(Res.string.app_name)
                        },
                        navigateBackIsPossible = backStack.size > 1,
                        onNavigateBackButtonClick = {
                            navigator.pop()
                        },
                        onOpenSourceLicensesButtonClick = {
                            navigator.goTo(OpenSourceLicenseListScreen)
                        },
                        onSourceCodeButtonClick = {
                            uriHandler.openUri("https://github.com/yuyuyuyuyu-dev/what-is-the-date-today")
                        },
                    )
                },
            ) { paddingValues ->
                CircuitCompositionLocals(circuit = koinInject()) {
                    NavigableCircuitContent(navigator, backStack, Modifier.padding(paddingValues))
                }
            }
        }
    }
}
