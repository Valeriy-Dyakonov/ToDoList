package com.example.myapplication.utils

import com.example.myapplication.model.AuthRequest
import org.junit.Test
import org.junit.jupiter.api.Assertions

class AuthRequestTest {
    @Test
    fun testConstructor() {
        val authRequest = AuthRequest("Login", "Password")
        Assertions.assertEquals("Login", authRequest.login)
    }
}