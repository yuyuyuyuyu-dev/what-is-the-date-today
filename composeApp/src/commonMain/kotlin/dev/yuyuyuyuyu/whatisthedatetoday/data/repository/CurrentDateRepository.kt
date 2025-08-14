package dev.yuyuyuyuyu.whatisthedatetoday.data.repository

import dev.yuyuyuyuyu.whatisthedatetoday.data.model.Date

interface CurrentDateRepository {
    fun getCurrentDate(): Date
}