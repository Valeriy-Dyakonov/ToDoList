package com.example.myapplication.model

import java.io.Serializable
import java.util.*

class Note(
    val id: Int?,
    val name: String,
    val category: String,
    val date: Date,
    val content: String,
    val done: Boolean
) : Serializable {
    constructor() : this(null, "", "", Date(), "", false)
}