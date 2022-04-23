package com.example.myapplication.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.myapplication.model.Note
import com.example.myapplication.utils.DateUtils

class DbManager(context: Context) {
    private val dbHelper: DbHelper = DbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    fun insert(name: String?, category: String?, content: String?, date: String?, done: String?) {
        val cv = ContentValues()
        cv.put(Constants.NAME, name)
        cv.put(Constants.CATEGORY, category)
        cv.put(Constants.CONTENT, content)
        cv.put(Constants.DATE, date)
        cv.put(Constants.DONE, done)
        db!!.insert(Constants.TABLE_NAME, null, cv)
    }

    fun update(note: Note) {
        val cv = ContentValues()
        cv.put(Constants.NAME, note.name)
        cv.put(Constants.CATEGORY, note.category)
        cv.put(Constants.CONTENT, note.content)
        cv.put(Constants.DATE, DateUtils.toString(note.date, DateUtils.DATE_WITH_TIME))
        cv.put(Constants.DONE, note.done.toString())
        db!!.update(Constants.TABLE_NAME, cv, "id = ?", Array(1) { note.id.toString() })
    }

    fun updateState(id: Int, state: Boolean) {
        val cv = ContentValues()
        cv.put(Constants.DONE, state.toString())
        db!!.update(Constants.TABLE_NAME, cv, "id = ?", Array(1) { id.toString() })
    }

    val readAll: ArrayList<Note>
        @SuppressLint("Range", "SimpleDateFormat")
        get() {
            val notes: ArrayList<Note> = ArrayList()
            val cursor = db!!.query(Constants.TABLE_NAME, null, null, null, null, null, null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(Constants.ID))
                val name = cursor.getString(cursor.getColumnIndex(Constants.NAME))
                val category = cursor.getString(cursor.getColumnIndex(Constants.CATEGORY))
                val date =
                    DateUtils.toDate(cursor.getString(cursor.getColumnIndex(Constants.DATE)), DateUtils.DATE_WITH_TIME)
                val content = cursor.getString(cursor.getColumnIndex(Constants.CONTENT))
                val done = cursor.getString(cursor.getColumnIndex(Constants.DONE)) == "true"
                notes.add(Note(id, name, category, date, content, done))
            }
            cursor.close()
            return notes
        }

    fun closeDb() {
        dbHelper.close()
    }

}