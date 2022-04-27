package com.example.myapplication.utils

import com.example.myapplication.model.Note
import org.junit.Test
import org.junit.jupiter.api.Assertions
import java.util.*

class NoteTest {
    @Test
    fun testConstructor() {
        val note = Note(
            1,
            "name",
            "category",
            Date(),
            "content",
            true
        )
        Assertions.assertTrue(note.done)

        val note_2 = Note()
        Assertions.assertFalse(note_2.done)
    }
}