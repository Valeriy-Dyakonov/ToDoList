package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.fragments.autoFragment.LoginFragment
import com.example.myapplication.fragments.autoFragment.RegisterFragment
import com.example.myapplication.model.FormModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val formModel: FormModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragment(R.id.authForm, LoginFragment.newInstance())
        initSubscriptions()
    }

    private fun initSubscriptions() {
        formModel.toRegisterClicked.observe(this) {
            setFragment(
                R.id.authForm,
                RegisterFragment.newInstance(),
                it
            )
        }
        formModel.toSignInClicked.observe(this) {
            setFragment(
                R.id.authForm,
                LoginFragment.newInstance(),
                it
            )
        }
        formModel.loginForm.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setFragment(frameLayout: Int, fragment: Fragment, replace: Boolean = true) {
        if (replace) {
            supportFragmentManager
                .beginTransaction()
                .replace(frameLayout, fragment)
                .commit()
        }
    }
}