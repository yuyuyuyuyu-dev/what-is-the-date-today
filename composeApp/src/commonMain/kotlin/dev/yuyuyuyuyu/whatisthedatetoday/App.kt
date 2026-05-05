package dev.yuyuyuyuyu.whatisthedatetoday

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import dev.yuyuyuyuyu.mymaterialtheme.MyMaterialTheme
import dev.yuyuyuyuyu.whatisthedatetoday.di.AppComponent
import dev.yuyuyuyuyu.whatisthedatetoday.di.createAppComponent
import dev.yuyuyuyuyu.whatisthedatetoday.ui.main.MainScreen

@Composable
@Preview
fun App() {
    val appComponent = remember { createAppComponent() }
    MyMaterialTheme {
        MainScreen(appComponent)
    }
}