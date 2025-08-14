package dev.yuyuyuyuyu.whatisthedatetoday.di

import com.slack.circuit.foundation.Circuit
import dev.yuyuyuyuyu.whatisthedatetoday.ui.openSourceLicenseList.OpenSourceLicenseList
import dev.yuyuyuyuyu.whatisthedatetoday.ui.openSourceLicenseList.OpenSourceLicenseListPresenter
import dev.yuyuyuyuyu.whatisthedatetoday.ui.openSourceLicenseList.OpenSourceLicenseListScreen
import dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday.WhatIsTheDateToday
import dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday.WhatIsTheDateTodayPresenter
import dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday.WhatIsTheDateTodayScreen
import org.koin.dsl.module

val uiModule = module {
    single {
        Circuit.Builder()

            .addPresenter<WhatIsTheDateTodayScreen, WhatIsTheDateTodayScreen.State>(
                WhatIsTheDateTodayPresenter(
                    currentDateRepository = get(),
                ),
            )
            .addUi<WhatIsTheDateTodayScreen, WhatIsTheDateTodayScreen.State> { state, navigator ->
                WhatIsTheDateToday(state, navigator)
            }

            .addPresenter<OpenSourceLicenseListScreen, OpenSourceLicenseListScreen.State>(
                OpenSourceLicenseListPresenter(),
            )
            .addUi<OpenSourceLicenseListScreen, OpenSourceLicenseListScreen.State> { _, modifier ->
                OpenSourceLicenseList(modifier)
            }

            .build()
    }
}
