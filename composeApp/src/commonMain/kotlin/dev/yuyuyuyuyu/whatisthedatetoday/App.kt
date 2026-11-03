package dev.yuyuyuyuyu.whatisthedatetoday

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import dev.yuyuyuyuyu.mycomposables.MyMaterialTheme
import dev.yuyuyuyuyu.whatisthedatetoday.di.createAppComponent
import dev.yuyuyuyuyu.whatisthedatetoday.ui.main.MainScreen
import kotlin.time.Clock

@Composable
@Preview
fun App(clock: Clock = Clock.System) {
    val appComponent = remember(clock) { createAppComponent(clock) }
    MyMaterialTheme { MainScreen(appComponent) }
}
