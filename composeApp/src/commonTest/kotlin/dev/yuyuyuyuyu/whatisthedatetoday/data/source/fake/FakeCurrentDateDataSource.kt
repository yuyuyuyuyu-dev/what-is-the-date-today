package dev.yuyuyuyuyu.whatisthedatetoday.data.source.fake

import dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource.CurrentDateDataSource
import kotlinx.datetime.LocalDate

class FakeCurrentDateDataSource : CurrentDateDataSource {
    override fun getCurrentDate(): LocalDate = LocalDate(2025, 7, 31)
}