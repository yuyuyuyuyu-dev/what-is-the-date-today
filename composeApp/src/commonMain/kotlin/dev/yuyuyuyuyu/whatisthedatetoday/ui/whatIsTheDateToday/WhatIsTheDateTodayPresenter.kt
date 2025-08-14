package dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.presenter.Presenter
import dev.yuyuyuyuyu.whatisthedatetoday.data.repository.CurrentDateRepository
import org.jetbrains.compose.resources.stringResource

class WhatIsTheDateTodayPresenter(
    private val currentDateRepository: CurrentDateRepository,
) : Presenter<WhatIsTheDateTodayScreen.State> {
    @Composable
    override fun present(): WhatIsTheDateTodayScreen.State {
        val currentDate = remember { currentDateRepository.getCurrentDate() }

        return WhatIsTheDateTodayScreen.State(
            japaneseEra = stringResource(currentDate.japaneseEra),
            year = currentDate.year,
            japaneseYear = currentDate.japaneseYear,
            month = currentDate.month,
            day = currentDate.day,
            dayOfWeek = stringResource(currentDate.dayOfWeek),
        )
    }
}
