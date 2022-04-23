package com.example.myapplication.enums

enum class DaysCategory(private val number: Int, private val label: String) {
    OVERDUE(1, "Overdue"),
    TODAY(2, "Next 24 Hours"),
    TOMORROW(3, "Coming days"),
    WEEK(4, "Week"),
    MONTH(5, "Month"),
    FUTURE(6, "Future");

    fun getLabel(): String {
        return label
    }

    companion object {
        fun getByNumber(number: Int): DaysCategory? {
            return values().find { it.number == number }
        }
    }
}