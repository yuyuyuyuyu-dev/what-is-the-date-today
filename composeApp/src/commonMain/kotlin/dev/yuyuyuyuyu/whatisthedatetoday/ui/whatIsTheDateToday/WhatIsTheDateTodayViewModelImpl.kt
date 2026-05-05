package dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yuyuyuyuyu.whatisthedatetoday.data.repository.CurrentDateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import org.jetbrains.compose.resources.getString

@Inject
class WhatIsTheDateTodayViewModelImpl(
    private val currentDateRepository: CurrentDateRepository,
) : ViewModel(), WhatIsTheDateTodayViewModel {

    private val _uiState =
        MutableStateFlow(
            WhatIsTheDateTodayUiState(
                japaneseEra = "",
                year = 0,
                japaneseYear = 0,
                month = 0,
                day = 0,
                dayOfWeek = "",
            )
        )
    override val uiState: StateFlow<WhatIsTheDateTodayUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val date = currentDateRepository.getCurrentDate()
            _uiState.value =
                WhatIsTheDateTodayUiState(
                    japaneseEra = getString(date.japaneseEra),
                    year = date.year,
                    japaneseYear = date.japaneseYear,
                    month = date.month,
                    day = date.day,
                    dayOfWeek = getString(date.dayOfWeek),
                )
        }
    }
}
