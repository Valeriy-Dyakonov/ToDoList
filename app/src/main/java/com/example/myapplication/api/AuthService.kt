package com.example.myapplication.api

import com.example.myapplication.model.AuthRequest
import com.example.myapplication.model.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/register")
    fun register(@Body request: AuthRequest): Call<AuthResponse>

    @POST("auth/sign_in")
    fun signIn(@Body request: AuthRequest): Call<AuthResponse>
}