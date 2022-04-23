package com.example.myapplication.fragments.mainFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.activities.LoginActivity
import com.example.myapplication.databinding.FragmentSettingsBinding
import java.util.*

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        binding.apply {
            val locales = Locale.getAvailableLocales().map { x -> x.displayName }
            val currentLocale =
                ConfigurationCompat.getLocales(resources.configuration)[0].displayName
            val adapter = ArrayAdapter(requireContext(), R.layout.list_item, locales)
            (textInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            localization.setText(currentLocale)
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