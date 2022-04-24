package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.api.AccessService
import com.example.myapplication.api.AuthService
import com.example.myapplication.api.retrofit.RestApiClient
import com.example.myapplication.api.security.SessionManager
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.fragments.autoFragment.LoginFragment
import com.example.myapplication.fragments.autoFragment.RegisterFragment
import com.example.myapplication.model.AuthRequest
import com.example.myapplication.model.AuthResponse
import com.example.myapplication.model.FormModel
import com.example.myapplication.utils.LocaleUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var authService: AuthService
    lateinit var accessService: AccessService
    private lateinit var sessionManager: SessionManager
    private val formModel: FormModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authService = RestApiClient.getAuthService(this)
        accessService = RestApiClient.getAccessService(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        sessionManager = SessionManager(this)
        setContentView(binding.root)
        setFragment(R.id.authForm, LoginFragment.newInstance())
        initSubscriptions()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LocaleUtils.attachBaseContext(it) })
    }

    private fun initSubscriptions() {

        formModel.toRegisterClicked.observe(this) {
            setFragment(R.id.authForm, RegisterFragment.newInstance(), it)
        }
        formModel.toSignInClicked.observe(this) {
            setFragment(R.id.authForm, LoginFragment.newInstance(), it)
        }
        formModel.loginForm.observe(this) {
            signInUp(authService.signIn(AuthRequest(it.login, it.password)));
        }
        formModel.registerForm.observe(this) {
            signInUp(authService.register(AuthRequest(it.login, it.password)));
        }
    }

    private fun signInUp(call: Call<AuthResponse>) {
        call.enqueue(object : Callback<AuthResponse> {
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(
                call: Call<AuthResponse>,
                response: Response<AuthResponse>
            ) {
                if (response.isSuccessful) {
                    sessionManager.saveAuthToken((response.body() as AuthResponse).token)
                    checkAccess()
                } else {
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun checkAccess() {
        accessService.checkAccess().enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful && response.body() as Boolean) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })
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