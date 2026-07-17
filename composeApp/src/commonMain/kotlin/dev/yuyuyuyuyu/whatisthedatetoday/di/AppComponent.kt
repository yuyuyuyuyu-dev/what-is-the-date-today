package dev.yuyuyuyuyu.whatisthedatetoday.di

import dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource.CurrentDateDataSource
import dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource.impl.CurrentDateDataSourceImpl
import dev.yuyuyuyuyu.whatisthedatetoday.data.repository.CurrentDateRepository
import dev.yuyuyuyuyu.whatisthedatetoday.data.repository.impl.CurrentDateRepositoryImpl
import dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday.WhatIsTheDateTodayViewModel
import dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday.WhatIsTheDateTodayViewModelImpl
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
abstract class AppComponent {
    abstract val whatIsTheDateTodayViewModel: WhatIsTheDateTodayViewModel

    @Provides
    fun provideCurrentDateDataSource(impl: CurrentDateDataSourceImpl): CurrentDateDataSource = impl

    @Provides
    fun provideCurrentDateRepository(impl: CurrentDateRepositoryImpl): CurrentDateRepository = impl

    @Provides
    fun provideWhatIsTheDateTodayViewModel(impl: WhatIsTheDateTodayViewModelImpl): WhatIsTheDateTodayViewModel = impl
}
