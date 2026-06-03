package dev.yuyuyuyuyu.whatisthedatetoday.ui.whatIsTheDateToday

import dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource.FakeCurrentDateDataSource
import dev.yuyuyuyuyu.whatisthedatetoday.data.repository.impl.CurrentDateRepositoryImpl
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import whatisthedatetoday.composeapp.generated.resources.Res
import whatisthedatetoday.composeapp.generated.resources.friday
import whatisthedatetoday.composeapp.generated.resources.monday
import whatisthedatetoday.composeapp.generated.resources.reiwa
import whatisthedatetoday.composeapp.generated.resources.saturday
import whatisthedatetoday.composeapp.generated.resources.sunday
import whatisthedatetoday.composeapp.generated.resources.thursday
import whatisthedatetoday.composeapp.generated.resources.tuesday
import whatisthedatetoday.composeapp.generated.resources.wednesday

/**
 * Sociable unit test: the view model is exercised together with its real collaborator
 * ([CurrentDateRepositoryImpl]); only the system-clock boundary is replaced with
 * [FakeCurrentDateDataSource]. This keeps the date logic (Reiwa year, day-of-week mapping)
 * deterministic without mocking the layers under test.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class WhatIsTheDateTodayViewModelImplTest {

    @BeforeTest
    fun setUp() {
        // The view model launches its init work on viewModelScope (Dispatchers.Main). The
        // unconfined test dispatcher runs that coroutine eagerly, so uiState is populated by the
        // time construction returns.
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun uiStateFor(date: LocalDate) =
        WhatIsTheDateTodayViewModelImpl(CurrentDateRepositoryImpl(FakeCurrentDateDataSource(date)))
            .uiState
            .value

    @Test
    fun exposesEveryFieldOfTheCurrentDate() {
        // 2024-01-03 is a Wednesday in Reiwa 6 (2024 - 2018).
        val state = uiStateFor(LocalDate(2024, 1, 3))

        assertEquals(Res.string.reiwa, state.japaneseEra)
        assertEquals(2024, state.year)
        assertEquals(6, state.japaneseYear)
        assertEquals(1, state.month)
        assertEquals(3, state.day)
        assertEquals(Res.string.wednesday, state.dayOfWeek)
    }

    @Test
    fun mapsEveryDayOfWeekToItsStringResource() {
        // 2024-01-01 (Mon) through 2024-01-07 (Sun) is a full Monday-first week.
        val expectedByOffset =
            listOf(
                Res.string.monday,
                Res.string.tuesday,
                Res.string.wednesday,
                Res.string.thursday,
                Res.string.friday,
                Res.string.saturday,
                Res.string.sunday,
            )

        expectedByOffset.forEachIndexed { offset, expected ->
            val state = uiStateFor(LocalDate(2024, 1, 1 + offset))
            assertEquals(expected, state.dayOfWeek)
        }
    }

    @Test
    fun computesReiwaYearRelativeTo2018() {
        assertEquals(1, uiStateFor(LocalDate(2019, 5, 1)).japaneseYear)
        assertEquals(8, uiStateFor(LocalDate(2026, 6, 3)).japaneseYear)
    }
}
