package dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource.impl

import dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource.CurrentDateDataSource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import me.tatarka.inject.annotations.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Inject
class CurrentDateDataSourceImpl : CurrentDateDataSource {
    @OptIn(ExperimentalTime::class)
    override fun getCurrentDate(): LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
}
