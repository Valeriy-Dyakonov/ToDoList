package com.example.myapplication.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    const val DATE_WITH_TIME = "dd/MM/yyyy HH:mm"
    const val DATE = "dd/MM/yyyy"
    const val TIME = "HH:mm"

    @SuppressLint("SimpleDateFormat")
    fun toString(date: Date, pattern: String) : String {
        return SimpleDateFormat(pattern).format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun toDate(date: String, pattern: String): Date {
        return SimpleDateFormat(pattern).parse(date)!!
    }

    @SuppressLint("SimpleDateFormat")
    fun concatDateAndTime(date: String, time: String): Date {
        return toDate("$date $time", DATE_WITH_TIME)
    }
}