package com.example.myapplication.fragments.autoFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.model.FormModel

class RegisterFragment : Fragment() {
    private val formModel: FormModel by activityViewModels()
    lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.toSignInButton.setOnClickListener {
            formModel.toSignInClicked.value = true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}