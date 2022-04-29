package com.example.myapplication.interfaces

import com.example.myapplication.model.Task

interface NoteClickListener {
    fun onNoteClick(task: Task)
    fun onNoteCheckClick(id: Int)
}