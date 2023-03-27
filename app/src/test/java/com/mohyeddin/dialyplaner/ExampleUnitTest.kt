package com.mohyeddin.dialyplaner

import com.mohyeddin.dialyplaner.common.util.MyCalendar
import ir.huri.jcal.JalaliCalendar
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CalenderExtensionUnitTest {
    @Test
    fun increasing_day_isCorrect() {
        assertEquals(MyCalendar("1401-12-29").also { it.day = it.day +5 }.toString(), MyCalendar("1401-12-29").increaseDay(5).toString())
    }

    @Test
    fun decreasing_dy_isCorrect() {
        assertEquals(JalaliCalendar().also { it.day = it.day - 1 }.toString(), MyCalendar().decreaseDay(1).toString())
    }

}