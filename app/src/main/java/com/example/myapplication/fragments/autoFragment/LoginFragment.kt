package com.example.myapplication.fragments.autoFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.model.FormModel

class LoginFragment : Fragment() {
    private val formModel: FormModel by activityViewModels()
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.toRegisterButton.setOnClickListener {
            formModel.toRegisterClicked.value = true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}