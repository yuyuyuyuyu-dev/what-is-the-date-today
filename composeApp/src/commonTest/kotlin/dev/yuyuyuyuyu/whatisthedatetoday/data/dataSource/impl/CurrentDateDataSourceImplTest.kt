package dev.yuyuyuyuyu.whatisthedatetoday.data.dataSource.impl

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

/**
 * Integration test for the boundary that [FakeCurrentDateDataSource] stands in for elsewhere: it
 * verifies the real implementation actually reports the system's current date (rather than a stale
 * or hard-coded value). Reading the control dates before and after tolerates a midnight rollover.
 */
@OptIn(ExperimentalTime::class)
class CurrentDateDataSourceImplTest {

    @Test
    fun returnsTheSystemCurrentDate() {
        val before = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val actual = CurrentDateDataSourceImpl().getCurrentDate()
        val after = Clock.System.todayIn(TimeZone.currentSystemDefault())

        assertTrue(
            actual == before || actual == after,
            "expected $actual to be the current system date (between $before and $after)",
        )
    }
}
