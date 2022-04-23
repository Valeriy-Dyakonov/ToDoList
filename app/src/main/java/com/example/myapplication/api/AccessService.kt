package com.example.myapplication.api

import retrofit2.Call
import retrofit2.http.GET

interface AccessService {
    @GET("hasAccess")
    fun checkAccess(): Call<Boolean>
}