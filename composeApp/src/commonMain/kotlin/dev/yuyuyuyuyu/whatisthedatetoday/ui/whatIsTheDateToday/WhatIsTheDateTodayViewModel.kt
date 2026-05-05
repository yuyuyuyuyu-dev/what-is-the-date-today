package dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday

import kotlinx.coroutines.flow.StateFlow

interface WhatIsTheDateTodayViewModel {
    val uiState: StateFlow<WhatIsTheDateTodayUiState>
}
