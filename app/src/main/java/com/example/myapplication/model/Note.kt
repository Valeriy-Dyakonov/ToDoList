package com.example.myapplication.model

import java.io.Serializable
import java.util.*

class Note(
    var id: Int?,
    val name: String,
    val category: String,
    val date: Date,
    val content: String,
    var done: Boolean
) : Serializable {
    constructor() : this(null, "", "", Date(), "", false)
}