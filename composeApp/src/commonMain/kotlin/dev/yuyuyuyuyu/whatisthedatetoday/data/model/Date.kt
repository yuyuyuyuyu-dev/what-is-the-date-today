package dev.yuyuyuyuyu.whatisthedatetoday.data.model

import org.jetbrains.compose.resources.StringResource

data class Date(
    val japaneseEra: StringResource,
    val year: Int,
    val japaneseYear: Int,
    val month: Int,
    val day: Int,
    val dayOfWeek: StringResource,
)
