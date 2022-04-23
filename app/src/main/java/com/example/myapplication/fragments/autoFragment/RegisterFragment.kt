package com.example.myapplication.fragments.autoFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.model.FormModel
import com.example.myapplication.model.LoginModel

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

        binding.registerButton.setOnClickListener {
            if (canRegister()) {
                formModel.registerForm.value =
                    LoginModel(binding.login.text.toString(), binding.password.text.toString())
            }
        }

        binding.repeatPassword.addTextChangedListener {
            validatePasswords()
        }

        binding.password.addTextChangedListener {
            validatePasswords()
        }

        binding.login.addTextChangedListener {
            if (!binding.login.text.toString().isEmpty()) {
                binding.login.error = null
            }
        }
    }

    private fun canRegister(): Boolean {
        binding.apply {
            if (login.text.toString().isEmpty()) {
                login.error = "Required"
            }
            if (password.text.toString().isEmpty()) {
                password.error = "Required"
            }
            if (repeatPassword.text.toString().isEmpty()) {
                repeatPassword.error = "Required"
            }
        }

        return binding.password.error == null &&
                binding.repeatPassword.error == null &&
                binding.login.error == null
    }

    private fun validatePasswords() {
        binding.apply {
            if (password.text.toString() != "" && repeatPassword.text.toString() != "" &&
                repeatPassword.text.toString() != password.text.toString()
            ) {
                password.error = "Passwords are not equals"
                repeatPassword.error = "Passwords are not equals"
            } else {
                password.error = null
                repeatPassword.error = null
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}