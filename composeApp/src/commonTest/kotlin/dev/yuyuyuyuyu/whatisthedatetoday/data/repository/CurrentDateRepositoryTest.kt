package dev.yuyuyuyuyu.whatisthedatetoday.data.repository

import dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource.fake.FakeCurrentDateDataSource
import dev.yuyuyuyuyu.whatisthedatetoday.data.model.Date
import dev.yuyuyuyuyu.whatisthedatetoday.data.repository.impl.CurrentDateRepositoryImpl
import whatisthedatetoday.composeapp.generated.resources.Res
import whatisthedatetoday.composeapp.generated.resources.reiwa
import whatisthedatetoday.composeapp.generated.resources.thursday
import kotlin.test.Test
import kotlin.test.assertEquals

class CurrentDateRepositoryTest {
    private val repository = CurrentDateRepositoryImpl(
        currentDateDataSource = FakeCurrentDateDataSource(),
    )

    @Test
    fun `getCurrentDate() should returns the current Date`() {
        // Arrange
        val expected = Date(
            japaneseEra = Res.string.reiwa,
            year = 2025,
            japaneseYear = 7,
            month = 7,
            day = 31,
            dayOfWeek = Res.string.thursday,
        )

        // Act
        val actual = repository.getCurrentDate()

        // Assert
        assertEquals(expected, actual)
    }
}
