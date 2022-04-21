package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.NoteItemBinding
import com.example.myapplication.enums.DaysCategory
import com.example.myapplication.model.Note
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    private val allNoteList = ArrayList<Note>()
    private val noteList = ArrayList<Note>()
    var category: DaysCategory? = DaysCategory.TODAY

    class NoteHolder(item: View) : RecyclerView.ViewHolder(item) {
        var binding = NoteItemBinding.bind(item)
        fun bind(note: Note) {
            binding.apply {
                noteTitle.text = note.name
                noteDate.text = note.date.toString()
                noteContent.text = note.content

                card.setOnLongClickListener {
                    deleteCardButton.visibility =
                        if (deleteCardButton.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun initNotes(notes: ArrayList<Note>) {
        noteList.clear()
        allNoteList.clear()
        allNoteList.addAll(notes)
        noteList.addAll(getByCategory())
        notifyDataSetChanged()
    }

    fun updateNotesByCategory() {
        noteList.clear()
        noteList.addAll(getByCategory())
        notifyDataSetChanged()
    }

    private fun getByCategory(): List<Note> {
        val date = Date()
        return when (category) {
            DaysCategory.OVERDUE -> allNoteList.filter { getDiff(date, it.date) < 0 }
            DaysCategory.TODAY -> allNoteList.filter { getDiff(date, it.date) == 0L }
            DaysCategory.TOMORROW -> allNoteList.filter { getDiff(date, it.date) == 1L }
            DaysCategory.WEEK -> allNoteList.filter { getDiff(date, it.date) in 2..7L }
            DaysCategory.MONTH -> allNoteList.filter { getDiff(date, it.date) in 8..31L }
            DaysCategory.FUTURE -> allNoteList.filter { getDiff(date, it.date) > 31L }
            null -> allNoteList
        }
    }

    private fun getDiff(time1: Date, time2: Date): Long {
        return TimeUnit.DAYS.convert(time2.time - time1.time, TimeUnit.MILLISECONDS)
    }
}