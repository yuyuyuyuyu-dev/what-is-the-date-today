package dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource

import kotlinx.datetime.LocalDate

/**
 * Test double for the system-clock-backed [CurrentDateDataSource]. It returns a fixed date so that
 * sociable tests of the higher layers (repository + view model) stay deterministic while exercising
 * the real collaborators.
 */
class FakeCurrentDateDataSource(private val date: LocalDate) : CurrentDateDataSource {
    override fun getCurrentDate(): LocalDate = date
}
