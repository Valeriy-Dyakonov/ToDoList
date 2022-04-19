package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.mainFragment.CalendarFragment
import com.example.myapplication.mainFragment.MapFragment
import com.example.myapplication.mainFragment.NotesListFragment
import com.example.myapplication.mainFragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNav()
        setFragment(R.id.mainFragmentLayout, NotesListFragment.newInstance())
    }

    private fun initBottomNav() {
        val bNav = binding.bNav
        bNav.selectedItemId = R.id.notes
        bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.notes -> setFragment(R.id.mainFragmentLayout, NotesListFragment.newInstance())
                R.id.calendar -> setFragment(R.id.mainFragmentLayout, CalendarFragment.newInstance())
                R.id.map -> setFragment(R.id.mainFragmentLayout, MapFragment.newInstance())
                R.id.profile -> setFragment(R.id.mainFragmentLayout, ProfileFragment.newInstance())
            }
            true
        }
    }

    private fun setFragment(frameLayout: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(frameLayout, fragment)
            .commit()
    }
}