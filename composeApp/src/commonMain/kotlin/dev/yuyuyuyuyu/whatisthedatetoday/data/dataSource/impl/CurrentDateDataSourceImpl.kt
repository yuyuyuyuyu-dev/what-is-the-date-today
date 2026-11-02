package dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource.impl

import dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource.CurrentDateDataSource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import me.tatarka.inject.annotations.Inject
import kotlin.time.Clock

@Inject
class CurrentDateDataSourceImpl(
    private val clock: Clock,
) : CurrentDateDataSource {
    override fun getCurrentDate(): LocalDate = clock.todayIn(TimeZone.currentSystemDefault())
}
