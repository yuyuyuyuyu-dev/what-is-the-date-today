package dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday

import org.jetbrains.compose.resources.StringResource

data class WhatIsTheDateTodayUiState(
    val japaneseEra: StringResource?,
    val year: Int,
    val japaneseYear: Int,
    val month: Int,
    val day: Int,
    val dayOfWeek: StringResource?,
)
