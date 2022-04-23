package com.example.myapplication.sqlite

object Constants {
    const val TABLE_NAME = "notes"
    const val DB_NAME = "database"
    const val DB_VERSION = 4
    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    const val ID = "id"
    const val NAME = "name"
    const val CATEGORY = "category"
    const val DATE = "date"
    const val CONTENT = "content"
    const val DONE = "done"
    const val TABLE_STRUCTURE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + " (" + ID + " INTEGER PRIMARY KEY, " + NAME + " TEXT, " + DATE + " TEXT, " + CATEGORY + " TEXT, " + CONTENT + " TEXT, " + DONE + " TEXT)")
}