package dev.yuyuyuyuyu.whatisthedatetoday

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.yuyuyuyuyu.mymaterialtheme.MyMaterialTheme
import dev.yuyuyuyuyu.whatisthedatetoday.ui.main.MainScreen

@Composable
@Preview
fun App() {
    MyMaterialTheme {
        MainScreen()
    }
}