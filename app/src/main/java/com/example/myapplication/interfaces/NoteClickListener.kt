package com.example.myapplication.interfaces

import com.example.myapplication.model.Note

interface NoteClickListener {
    fun onNoteClick(note: Note)
}