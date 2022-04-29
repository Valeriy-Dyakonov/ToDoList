package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.NoteItemBinding
import com.example.myapplication.enums.DaysCategory
import com.example.myapplication.interfaces.NoteClickListener
import com.example.myapplication.model.Task
import com.example.myapplication.sqlite.DbManager
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class NoteAdapter(
    private val noteClickListener: NoteClickListener,
    private var dbManager: DbManager
) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    private var allNoteList = ArrayList<Task>()
    private var noteList = ArrayList<Task>()
    var timePeriod: DaysCategory = DaysCategory.TODAY
    var category: String = ""

    class NoteHolder(
        item: View,
        private var noteClickListener: NoteClickListener,
        private var dbManager: DbManager
    ) : RecyclerView.ViewHolder(item) {
        private var binding = NoteItemBinding.bind(item)

        @SuppressLint("SimpleDateFormat")
        var formatter = SimpleDateFormat("dd MMM yyyy HH:mm")

        fun bind(task: Task) {
            binding.apply {
                noteTitle.text = task.name
                noteDate.text = formatter.format(task.date)
                noteContent.text = task.content
                bookmark.text = task.category
                bookmark.visibility = if (task.category != "") View.VISIBLE else View.INVISIBLE
                checkBox.isChecked = task.done
                card.isChecked = false

                card.setOnLongClickListener {
                    card.isChecked = !card.isChecked
                    task.id?.let { x -> noteClickListener.onNoteCheckClick(x) }
                    true
                }

                card.setOnClickListener {
                    noteClickListener.onNoteClick(task)
                }

                checkBox.setOnClickListener {
                    task.done = checkBox.isChecked
                    task.id?.let { x -> dbManager.updateState(x, task.done) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(view, noteClickListener, dbManager)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun getCategories(): Array<String> {
        return allNoteList.filter { x -> x.category.isNotEmpty() }.map { x -> x.category }
            .distinct().toTypedArray()
    }

    fun editedNote(task: Task) {
        noteList = noteList.filter { it.id != task.id } as ArrayList<Task>
        noteList.add(task)
        allNoteList = allNoteList.filter { it.id != task.id } as ArrayList<Task>
        allNoteList.add(task)
        updateView()
    }

    fun addNote(task: Task) {
        noteList.add(task)
        allNoteList.add(task)
        timePeriod = DaysCategory.TODAY
        category = ""
        updateNotesByTimePeriod()
    }

    fun initNotes(tasks: ArrayList<Task>) {
        noteList.clear()
        allNoteList.clear()
        allNoteList.addAll(tasks)
        noteList.addAll(getByTimePeriod())
        updateView()
    }

    fun updateNotesByTimePeriod() {
        noteList.clear()
        noteList.addAll(getByTimePeriod())
        updateView()
    }

    fun updateNotesByCategory() {
        noteList.clear()
        noteList.addAll(getByCategory())
        updateView()
    }

    fun deleteByIds(ids: ArrayList<Int>) {
        noteList = noteList.filter { !ids.contains(it.id) } as ArrayList<Task>
        allNoteList = allNoteList.filter { !ids.contains(it.id) } as ArrayList<Task>
        updateView()
    }

    private fun getByTimePeriod(): List<Task> {
        val date = Date()
        return when (timePeriod) {
            DaysCategory.OVERDUE -> allNoteList.filter { getDiff(date, it.date) < 0 }
            DaysCategory.TODAY -> allNoteList.filter { getDiff(date, it.date) == 0L }
            DaysCategory.TOMORROW -> allNoteList.filter { getDiff(date, it.date) == 1L }
            DaysCategory.WEEK -> allNoteList.filter { getDiff(date, it.date) in 2..7L }
            DaysCategory.MONTH -> allNoteList.filter { getDiff(date, it.date) in 8..31L }
            DaysCategory.FUTURE -> allNoteList.filter { getDiff(date, it.date) > 31L }
        }
    }

    private fun getByCategory(): List<Task> {
        return allNoteList.filter { it.category.trim().lowercase() == category.trim().lowercase() }
    }

    private fun getDiff(time1: Date, time2: Date): Long {
        return TimeUnit.DAYS.convert(time2.time - time1.time, TimeUnit.MILLISECONDS)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateView() {
        notifyDataSetChanged()
    }
}