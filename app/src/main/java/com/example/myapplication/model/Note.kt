package com.example.myapplication.model

import java.io.Serializable
import java.util.*

class Note(val name: String, val date: Date, val content: String, val done: Boolean): Serializable {
}