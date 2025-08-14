package dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.stringResource
import whatisthedatetoday.composeapp.generated.resources.Res
import whatisthedatetoday.composeapp.generated.resources.date_format

@Composable
fun WhatIsTheDateToday(state: WhatIsTheDateTodayScreen.State, modifier: Modifier = Modifier) = Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
) {
    Text(
        text = stringResource(
            Res.string.date_format,
            state.year,
            state.japaneseEra,
            state.japaneseYear,
            state.month.toString().padStart(2, '0'),
            state.day.toString().padStart(2, '0'),
            state.dayOfWeek,
        ),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium,
    )
}
