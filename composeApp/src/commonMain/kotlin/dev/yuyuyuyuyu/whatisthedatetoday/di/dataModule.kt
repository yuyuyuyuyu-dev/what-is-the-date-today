package dev.yuyuyuyuyu.whatisthedatetoday.di

import dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource.CurrentDateDataSource
import dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource.impl.CurrentDateDataSourceImpl
import dev.yuyuyuyuyu.whatisthedatetoday.data.repository.CurrentDateRepository
import dev.yuyuyuyuyu.whatisthedatetoday.data.repository.impl.CurrentDateRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    // repositories
    single<CurrentDateRepository> {
        CurrentDateRepositoryImpl(currentDateDataSource = get())
    }

    // data sources
    singleOf<CurrentDateDataSource>(::CurrentDateDataSourceImpl)
}
