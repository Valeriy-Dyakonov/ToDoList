package com.example.myapplication.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions
import java.util.*

class DateUtilsTest {
    @Test
    fun testToString() {
        val instance = Calendar.getInstance()
        val day = instance.get(Calendar.DAY_OF_MONTH)
        val month = instance.get(Calendar.MONTH) + 1
        val year = instance.get(Calendar.YEAR)
        val HOUR = instance.get(Calendar.HOUR_OF_DAY)
        val MINUTE = instance.get(Calendar.MINUTE)
        val time = DateUtils.toDate(DateUtils.toString(instance.time, DateUtils.DATE_WITH_TIME), DateUtils.DATE_WITH_TIME)

        val toString = DateUtils.toString(time, DateUtils.DATE_WITH_TIME)

        val dateString = (if (day < 10) "0$day" else day.toString()) +
                "/" + (if (month < 10) "0$month" else month.toString()) +
                "/" + year.toString()
        val timeString =
            (if (HOUR < 10) "0$HOUR" else HOUR.toString()) + ":" + (if (MINUTE < 10) "0$MINUTE" else MINUTE.toString())

        val value = "$dateString $timeString"
        Assertions.assertEquals(value, toString)

        val toDate = DateUtils.toDate(value, DateUtils.DATE_WITH_TIME)
        Assertions.assertEquals(time, toDate)

        val concatDateAndTime = DateUtils.concatDateAndTime(dateString, timeString)
        Assertions.assertEquals(time, concatDateAndTime)
    }
}