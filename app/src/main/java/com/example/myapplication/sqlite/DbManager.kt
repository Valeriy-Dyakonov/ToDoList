package com.example.myapplication.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.myapplication.model.Note
import java.text.SimpleDateFormat
import java.util.*

class DbManager(context: Context) {
    private val dbHelper: DbHelper = DbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    fun insertToDb(name: String?, category: String?, content: String?, date: String?, done: String?) {
        val cv = ContentValues()
        cv.put(Constants.NAME, name)
        cv.put(Constants.CATEGORY, category)
        cv.put(Constants.CONTENT, content)
        cv.put(Constants.DATE, date)
        cv.put(Constants.DONE, done)
        db!!.insert(Constants.TABLE_NAME, null, cv)
    }

    val fromDb: ArrayList<Note>
        @SuppressLint("Range", "SimpleDateFormat")
        get() {
            val notes: ArrayList<Note> = ArrayList()
            val cursor = db!!.query(Constants.TABLE_NAME, null, null, null, null, null, null)
            val simpleDateFormat = SimpleDateFormat("dd mmm yyyy HH:mm")
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(Constants.ID))
                val name = cursor.getString(cursor.getColumnIndex(Constants.NAME))
                val category = cursor.getString(cursor.getColumnIndex(Constants.CATEGORY))
                val date = simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(Constants.DATE)))
                val content = cursor.getString(cursor.getColumnIndex(Constants.CONTENT))
                val done = cursor.getString(cursor.getColumnIndex(Constants.CONTENT)) == "true"
                notes.add(Note(id, name, category, date!!, content, done))
            }
            cursor.close()
            return notes
        }

    fun closeDb() {
        dbHelper.close()
    }

}