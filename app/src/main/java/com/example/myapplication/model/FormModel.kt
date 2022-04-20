package com.example.myapplication.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class FormModel: ViewModel() {
        val toRegisterClicked: MutableLiveData<Boolean> by lazy {
                MutableLiveData<Boolean>()
        }

        val toSignInClicked: MutableLiveData<Boolean> by lazy {
                MutableLiveData<Boolean>()
        }

        val loginForm: MutableLiveData<LoginModel> by lazy {
                MutableLiveData<LoginModel>()
        }

        val registerForm: MutableLiveData<LoginModel> by lazy {
                MutableLiveData<LoginModel>()
        }
}