package dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource

import kotlinx.datetime.LocalDate

interface CurrentDateDataSource {
    fun getCurrentDate(): LocalDate
}
