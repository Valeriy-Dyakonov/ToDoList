package com.example.myapplication.utils

import com.example.myapplication.model.Task
import org.junit.Test
import org.junit.jupiter.api.Assertions
import java.util.*

class TaskTest {
    @Test
    fun testConstructor() {
        val note = Task(
            1,
            "name",
            "category",
            Date(),
            "content",
            true
        )
        Assertions.assertTrue(note.done)

        val note_2 = Task()
        Assertions.assertFalse(note_2.done)
    }
}