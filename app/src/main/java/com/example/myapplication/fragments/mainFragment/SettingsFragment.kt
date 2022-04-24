package com.example.myapplication.fragments.mainFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.activities.LoginActivity
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.databinding.FragmentSettingsBinding
import com.example.myapplication.utils.LocaleUtils
import java.util.*


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        binding.apply {
            val currentLocale = ConfigurationCompat.getLocales(resources.configuration)[0].language

            enButton.setOnClickListener {
                if (currentLocale != "en") {
                    LocaleUtils.updateLanguage(requireContext(), Locale.ENGLISH)
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    activity?.finish()
                }
            }

            ruButton.setOnClickListener {
                if (currentLocale != "ru") {
                    LocaleUtils.updateLanguage(requireContext(), Locale("ru"))
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    activity?.finish()
                }
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