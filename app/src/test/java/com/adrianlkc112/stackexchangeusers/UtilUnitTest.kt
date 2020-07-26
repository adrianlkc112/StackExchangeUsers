package com.adrianlkc112.stackexchangeusers

import com.adrianlkc112.stackexchangeusers.util.DateHelperUtil
import org.junit.Test

import org.junit.Assert.*
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset

/**
 * Local unit test for util, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UtilUnitTest {
    @Test
    fun dateHelper_isCorrect() {
        assertEquals(DateHelperUtil.convertEpochToDisplay(1595747749,
            zone = ZoneId.ofOffset("GMT", ZoneOffset.of("+08:00"))),
            "2020-07-26 15:15:49")

        assertEquals(DateHelperUtil.convertEpochToDisplay(1588949480,
            zone = ZoneId.ofOffset("GMT", ZoneOffset.of("+01:00"))),
            "2020-05-08 15:51:20")
    }
}