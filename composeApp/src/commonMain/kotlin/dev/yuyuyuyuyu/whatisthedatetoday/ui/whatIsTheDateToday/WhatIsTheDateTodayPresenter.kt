package dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.presenter.Presenter

class WhatIsTheDateTodayPresenter : Presenter<WhatIsTheDateTodayScreen.State> {
    @Composable
    override fun present(): WhatIsTheDateTodayScreen.State {
        return WhatIsTheDateTodayScreen.State
    }
}
