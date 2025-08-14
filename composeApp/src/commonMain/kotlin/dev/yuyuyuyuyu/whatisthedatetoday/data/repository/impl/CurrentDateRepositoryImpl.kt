package dev.yuyuyuyuyu.whatisthedatetoday.data.repository.impl

import dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource.CurrentDateDataSource
import dev.yuyuyuyuyu.whatisthedatetoday.data.model.Date
import dev.yuyuyuyuyu.whatisthedatetoday.data.repository.CurrentDateRepository
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.number
import whatisthedatetoday.composeapp.generated.resources.Res
import whatisthedatetoday.composeapp.generated.resources.friday
import whatisthedatetoday.composeapp.generated.resources.monday
import whatisthedatetoday.composeapp.generated.resources.reiwa
import whatisthedatetoday.composeapp.generated.resources.saturday
import whatisthedatetoday.composeapp.generated.resources.sunday
import whatisthedatetoday.composeapp.generated.resources.thursday
import whatisthedatetoday.composeapp.generated.resources.tuesday
import whatisthedatetoday.composeapp.generated.resources.wednesday

class CurrentDateRepositoryImpl(
    private val currentDateDataSource: CurrentDateDataSource,
) : CurrentDateRepository {
    override fun getCurrentDate(): Date {
        val today = currentDateDataSource.getCurrentDate()

        return Date(
            japaneseEra = Res.string.reiwa,
            year = today.year,
            japaneseYear = today.year - 2018,
            month = today.month.number,
            day = today.day,
            dayOfWeek = when (today.dayOfWeek) {
                DayOfWeek.MONDAY -> Res.string.monday
                DayOfWeek.TUESDAY -> Res.string.tuesday
                DayOfWeek.WEDNESDAY -> Res.string.wednesday
                DayOfWeek.THURSDAY -> Res.string.thursday
                DayOfWeek.FRIDAY -> Res.string.friday
                DayOfWeek.SATURDAY -> Res.string.saturday
                DayOfWeek.SUNDAY -> Res.string.sunday
            },
        )
    }
}
