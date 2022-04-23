package com.example.myapplication.fragments.mainFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.activities.EditActivity
import com.example.myapplication.adapter.NoteAdapter
import com.example.myapplication.databinding.FragmentNotesListBinding
import com.example.myapplication.enums.DaysCategory
import com.example.myapplication.interfaces.NoteClickListener
import com.example.myapplication.model.Note

class NotesListFragment : Fragment(), NoteClickListener {
    private lateinit var binding: FragmentNotesListBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesListBinding.inflate(inflater)
        initLauncherAndAdapter()
        initRecyclerView()
        initListeners()
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesListFragment()
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(requireContext(), EditActivity::class.java).apply {
            putExtra("note", note)
        }
        editLauncher.launch(intent)
    }

    private fun initLauncherAndAdapter() {
        adapter = NoteAdapter(this)
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {

            }
        }

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
            addNote.setOnClickListener {
                editLauncher.launch(Intent(requireContext(), EditActivity::class.java))
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            noteRcView.layoutManager = LinearLayoutManager(requireContext())
            noteRcView.adapter = adapter
        }
    }
}