package dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.stringResource
import whatisthedatetoday.composeapp.generated.resources.Res
import whatisthedatetoday.composeapp.generated.resources.date_format

@Composable
fun WhatIsTheDateTodayScreen(
    viewModel: WhatIsTheDateTodayViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val japaneseEraStr = uiState.japaneseEra?.let { stringResource(it) } ?: ""
        val dayOfWeekStr = uiState.dayOfWeek?.let { stringResource(it) } ?: ""

        Text(
            text =
                stringResource(
                    Res.string.date_format,
                    uiState.year,
                    japaneseEraStr,
                    uiState.japaneseYear,
                    uiState.month.toString().padStart(2, '0'),
                    uiState.day.toString().padStart(2, '0'),
                    dayOfWeekStr,
                ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}
