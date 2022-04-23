package com.example.myapplication.fragments.mainFragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.os.ConfigurationCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.activities.LoginActivity
import com.example.myapplication.databinding.FragmentSettingsBinding
import com.example.myapplication.utils.LocaleUtils


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        binding.apply {
            val currentLocale =
                ConfigurationCompat.getLocales(resources.configuration)[0].language

            val en = resources.getString(R.string.english)
            val ru = resources.getString(R.string.russian)

            val items = listOf(ru, en)
            val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
            (textInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            localization.setText(if (currentLocale == "ru") ru else en)

            localization.addTextChangedListener {
                LocaleUtils.setAppLocale(requireActivity())
            }

            logout.setOnClickListener {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                activity?.finish()
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}