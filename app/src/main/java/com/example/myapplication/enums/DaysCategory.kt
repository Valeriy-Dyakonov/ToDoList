package com.example.myapplication.enums

enum class DaysCategory(private val number: Int) {
    OVERDUE(1),
    TODAY(2),
    TOMORROW(3),
    WEEK(4),
    MONTH(5),
    FUTURE(6);


    companion object {
        fun getByNumber(number: Int): DaysCategory? {
            return values().find { it.number == number }
        }
    }
}