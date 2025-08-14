package dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen

data object WhatIsTheDateTodayScreen : Screen {
    data class State(
        val japaneseEra: String,
        val year: Int,
        val japaneseYear: Int,
        val month: Int,
        val day: Int,
        val dayOfWeek: String,
    ) : CircuitUiState
}
