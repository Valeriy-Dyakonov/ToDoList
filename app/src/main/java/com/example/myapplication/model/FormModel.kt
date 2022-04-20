package com.example.myapplication.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FormModel: ViewModel() {
        val toRegisterClicked: MutableLiveData<Boolean> by lazy {
                MutableLiveData<Boolean>()
        }

        val toSignInClicked: MutableLiveData<Boolean> by lazy {
                MutableLiveData<Boolean>()
        }
}