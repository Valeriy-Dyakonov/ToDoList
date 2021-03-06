package com.example.myapplication.fragments.mainFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.activities.EditActivity
import com.example.myapplication.adapter.NoteAdapter
import com.example.myapplication.databinding.FragmentNotesListBinding
import com.example.myapplication.enums.DaysCategory
import com.example.myapplication.enums.OperationType
import com.example.myapplication.interfaces.NoteClickListener
import com.example.myapplication.model.Task
import com.example.myapplication.sqlite.DbManager
import com.example.myapplication.utils.DateUtils

class NotesListFragment : Fragment(), NoteClickListener {
    private lateinit var binding: FragmentNotesListBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteAdapter
    private lateinit var dbManager: DbManager
    private var toDeleteList = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dbManager = DbManager(requireContext())
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

    override fun onNoteClick(task: Task) {
        val intent = Intent(requireContext(), EditActivity::class.java).apply {
            putExtra("categories", adapter.getCategories())
            putExtra("note", task)
        }
        editLauncher.launch(intent)
    }

    override fun onNoteCheckClick(id: Int) {
        if (toDeleteList.find { x -> x == id } != null) {
            toDeleteList.remove(id)
        } else {
            toDeleteList.add(id)
        }
        updateAddButtonView()
    }

    private fun updateAddButtonView() {
        binding.addNote.icon = ContextCompat.getDrawable(
            requireContext(),
            if (toDeleteList.isEmpty()) R.drawable.ic_add else R.drawable.ic_delete_24
        )
    }

    private fun initLauncherAndAdapter() {
        adapter = NoteAdapter(this, dbManager)
        dbManager.openDb()
        adapter.initNotes(dbManager.readAll)
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                val type = OperationType.valueOf(it.data?.getSerializableExtra("type").toString())
                val note = it.data?.getSerializableExtra("note") as Task
                if (type == OperationType.ADD) {
                    note.id = dbManager.insert(
                        note.name,
                        note.category,
                        note.content,
                        DateUtils.toString(note.date, DateUtils.DATE_WITH_TIME),
                        note.done.toString()
                    ).toInt()
                    adapter.addNote(note)
                } else if (type == OperationType.EDIT) {
                    dbManager.update(note)
                    adapter.editedNote(note)
                }
            }
        }

    }

    private fun initListeners() {
        binding.apply {
            topAppBar.setOnClickListener {
                updateDrawerMenu()
                drawerLayout.openDrawer(GravityCompat.START)
            }
            navigationView.setNavigationItemSelectedListener {
                if (it.order > navigationView.menu[0].subMenu.size()) {
                    adapter.category = it.title.toString()
                    adapter.updateNotesByCategory()
                } else {
                    adapter.timePeriod = DaysCategory.getByNumber(it.order)!!
                    adapter.updateNotesByTimePeriod()
                }
                drawerLayout.closeDrawers()
                true
            }
            addNote.setOnClickListener {
                if (toDeleteList.isEmpty()) {
                    val intent = Intent(requireContext(), EditActivity::class.java).apply {
                        putExtra("categories", adapter.getCategories())
                    }
                    editLauncher.launch(intent)
                } else {
                    dbManager.deleteAllById(toDeleteList)
                    adapter.deleteByIds(toDeleteList)
                    toDeleteList.clear()
                    updateAddButtonView()
                }
            }
        }
    }

    private fun updateDrawerMenu() {
        binding.apply {
            var start = navigationView.menu[0].subMenu.size()
            val subMenu = navigationView.menu[1].subMenu
            subMenu.clear()
            for (category in adapter.getCategories()) {
                start++
                subMenu.add(start, start, start, category)
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            noteRcView.layoutManager =
                if (resources.configuration.orientation == 2) GridLayoutManager(
                    requireContext(),
                    2
                ) else LinearLayoutManager(requireContext())
            noteRcView.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }
}