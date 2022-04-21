package com.example.myapplication.fragments.mainFragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.NoteAdapter
import com.example.myapplication.api.NotesService
import com.example.myapplication.api.retrofit.RestApiClient
import com.example.myapplication.databinding.FragmentNotesListBinding
import com.example.myapplication.enums.DaysCategory
import com.example.myapplication.model.Note
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class NotesListFragment : Fragment() {
    private lateinit var binding: FragmentNotesListBinding
    lateinit var notesService: NotesService
    val adapter = NoteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesListBinding.inflate(inflater)
        notesService = RestApiClient.notesService
        initRecyclerView()
        initListeners()
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesListFragment()
    }

    private fun initListeners() {
        binding.apply {
            topAppBar.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            navigationView.setNavigationItemSelectedListener {
                adapter.category = DaysCategory.getByNumber(it.order)!!
                adapter.updateNotesByCategory()
                drawerLayout.closeDrawers()
                true
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            noteRcView.layoutManager = LinearLayoutManager(requireContext())
            noteRcView.adapter = adapter
            getNotes()
        }
    }

    private fun getNotes() {
        notesService.getNotes().enqueue(object : Callback<ArrayList<Note>> {
            override fun onFailure(call: Call<ArrayList<Note>>, t: Throwable) {}
            override fun onResponse(
                call: Call<ArrayList<Note>>,
                response: Response<ArrayList<Note>>
            ) {
                val notes = response.body() as ArrayList<Note>
                adapter.initNotes(notes)
            }
        })
    }
}