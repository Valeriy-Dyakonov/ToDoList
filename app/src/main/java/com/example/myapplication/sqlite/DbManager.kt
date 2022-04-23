package com.example.myapplication.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.myapplication.model.Note
import java.util.*

class DbManager(context: Context) {
    private val dbHelper: DbHelper = DbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    fun insertToDb(name: String?, category: String?, content: String?) {
        val cv = ContentValues()
        cv.put(Constants.NAME, name)
        cv.put(Constants.CATEGORY, category)
        cv.put(Constants.CONTENT, content)
        db!!.insert(Constants.TABLE_NAME, null, cv)
    }

    val fromDb: ArrayList<Note>
        @SuppressLint("Range")
        get() {
            val notes: ArrayList<Note> = ArrayList()
            val cursor = db!!.query(Constants.TABLE_NAME, null, null, null, null, null, null)
            while (cursor.moveToNext()) {
                val string = cursor.getString(cursor.getColumnIndex(Constants.NAME))
                val category = cursor.getString(cursor.getColumnIndex(Constants.CATEGORY))
                val content = cursor.getString(cursor.getColumnIndex(Constants.CONTENT))
                notes.add(Note(string, Date(), content, true))
            }
            cursor.close()
            return notes
        }

    fun closeDb() {
        dbHelper.close()
    }

}