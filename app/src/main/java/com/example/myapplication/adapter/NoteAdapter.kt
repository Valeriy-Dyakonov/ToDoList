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
import com.example.myapplication.model.Note
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

class NoteAdapter(private val noteClickListener: NoteClickListener) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    private val allNoteList = ArrayList<Note>()
    private val noteList = ArrayList<Note>()
    var category: DaysCategory = DaysCategory.TODAY

    class NoteHolder(item: View, private var noteClickListener: NoteClickListener) : RecyclerView.ViewHolder(item) {
        var binding = NoteItemBinding.bind(item)
        @SuppressLint("SimpleDateFormat")
        var formatter = SimpleDateFormat("dd MMM yyyy HH:mm")

        fun bind(note: Note) {
            binding.apply {
                noteTitle.text = note.name
                noteDate.text = formatter.format(note.date)
                noteContent.text = note.content
                bookmark.visibility = View.GONE
                checkBox.isChecked = note.done

                card.setOnLongClickListener {
                    card.isChecked = !card.isChecked
                    true
                }

                card.setOnClickListener {
                    noteClickListener.onNoteClick(note)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(view, noteClickListener)
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
        }
    }

    private fun getDiff(time1: Date, time2: Date): Long {
        return TimeUnit.DAYS.convert(time2.time - time1.time, TimeUnit.MILLISECONDS)
    }
}