package com.example.myapplication.utils

import com.example.myapplication.model.LoginModel
import org.junit.Test
import org.junit.jupiter.api.Assertions

class LoginModelTest {
    @Test
    fun testConstructor() {
        val loginModel = LoginModel("Login", "Password")
        Assertions.assertEquals("Login", loginModel.login)
    }
}