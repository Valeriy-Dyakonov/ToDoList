package com.example.myapplication.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.NoteAdapter
import com.example.myapplication.databinding.FragmentNotesListBinding
import com.example.myapplication.model.Note

class NotesListFragment : Fragment() {
    lateinit var binding: FragmentNotesListBinding
    private val adapter = NoteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesListBinding.inflate(inflater)
        initRecyclerView()
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesListFragment()
    }

    private fun initRecyclerView() {
        binding.apply {
            noteRcView.layoutManager = LinearLayoutManager(requireContext())
            noteRcView.adapter = adapter

        }
    }
}