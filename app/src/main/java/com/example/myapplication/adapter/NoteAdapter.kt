package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.NoteItemBinding
import com.example.myapplication.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    val noteList = ArrayList<Note>()

    class NoteHolder(item: View) : RecyclerView.ViewHolder(item) {
        var binding = NoteItemBinding.bind(item)
        fun bind(note: Note) {
            binding.noteTitle.text = note.name
            binding.noteDate.text = note.date.toString()
            binding.noteContent.text = note.content
            binding.apply {
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

    fun addNote(note: Note) {
        noteList.add(note)
        notifyDataSetChanged()
    }

    fun initNotes(notes: ArrayList<Note>) {
        noteList.clear()
        noteList.addAll(notes)
        notifyDataSetChanged()
    }
}