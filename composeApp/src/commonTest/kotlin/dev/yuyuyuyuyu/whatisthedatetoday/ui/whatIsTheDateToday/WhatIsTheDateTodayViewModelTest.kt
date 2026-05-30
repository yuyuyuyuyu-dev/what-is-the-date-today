package dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday

import dev.yuyuyuyuyu.whatisthedatetoday.data.repository.impl.CurrentDateRepositoryImpl
import dev.yuyuyuyuyu.whatisthedatetoday.data.source.fake.FakeCurrentDateDataSource
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import whatisthedatetoday.composeapp.generated.resources.Res
import whatisthedatetoday.composeapp.generated.resources.reiwa
import whatisthedatetoday.composeapp.generated.resources.thursday

class WhatIsTheDateTodayViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `ViewModel should initialize uiState correctly`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        // Arrange
        val repository =
            CurrentDateRepositoryImpl(currentDateDataSource = FakeCurrentDateDataSource())

        // Act
        val viewModel = WhatIsTheDateTodayViewModelImpl(currentDateRepository = repository)
        // Wait for coroutines in ViewModel's init block to finish
        advanceUntilIdle()

        // Assert
        val actual = viewModel.uiState.value
        val expected =
            WhatIsTheDateTodayUiState(
                japaneseEra = Res.string.reiwa,
                year = 2025,
                japaneseYear = 7,
                month = 7,
                day = 31,
                dayOfWeek = Res.string.thursday,
            )
        assertEquals(expected, actual)
    }
}
