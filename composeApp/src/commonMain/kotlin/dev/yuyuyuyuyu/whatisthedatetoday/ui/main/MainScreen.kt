package dev.yuyuyuyuyu.whatisthedatetoday.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.mikepenz.aboutlibraries.ui.compose.produceLibraries
import dev.yuyuyuyuyu.mycomposables.MyScaffold
import dev.yuyuyuyuyu.whatisthedatetoday.di.AppComponent
import dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday.WhatIsTheDateTodayScreen
import org.jetbrains.compose.resources.stringResource
import whatisthedatetoday.composeapp.generated.resources.Res
import whatisthedatetoday.composeapp.generated.resources.app_name

@Composable
fun MainScreen(appComponent: AppComponent) {
    val uriHandler = LocalUriHandler.current

    val libraries by produceLibraries {
        Res.readBytes("files/aboutlibraries.json").decodeToString()
    }

    MyScaffold(
        title = stringResource(Res.string.app_name),
        // A dependency shared by several targets is listed once per target, so the
        // repeats are collapsed before the list is shown.
        libraries = libraries?.let { libs -> libs.copy(libraries = libs.libraries.distinctBy { it.name }) },
        onSourceCodeButtonClick = {
            uriHandler.openUri("https://github.com/yuyuyuyuyu-dev/what-is-the-date-today")
        },
    ) { innerPadding ->
        WhatIsTheDateTodayScreen(
            viewModel = appComponent.whatIsTheDateTodayViewModel,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
