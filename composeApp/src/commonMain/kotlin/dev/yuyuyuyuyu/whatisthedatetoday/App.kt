package dev.yuyuyuyuyu.whatisthedatetoday

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import dev.yuyuyuyuyu.mycomposables.MyMaterialTheme
import dev.yuyuyuyuyu.whatisthedatetoday.di.createAppComponent
import dev.yuyuyuyuyu.whatisthedatetoday.ui.main.MainScreen
import org.jetbrains.compose.resources.stringResource
import whatisthedatetoday.composeapp.generated.resources.Res
import whatisthedatetoday.composeapp.generated.resources.language_tag
import kotlin.time.Clock

@Composable
@Preview
fun App(clock: Clock = Clock.System) {
    val appComponent = remember(clock) { createAppComponent(clock) }
    val language = stringResource(Res.string.language_tag)
    LaunchedEffect(language) {
        if (language.isNotEmpty()) setDocumentLanguage(language)
    }
    MyMaterialTheme { MainScreen(appComponent) }
}
