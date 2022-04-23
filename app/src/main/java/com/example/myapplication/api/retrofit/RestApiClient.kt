package com.example.myapplication.api.retrofit

import android.content.Context
import com.example.myapplication.api.AccessService
import com.example.myapplication.api.AuthService

object RestApiClient {
    private var BASE_URL = "http://10.0.2.2:8080/api/"

    fun getAuthService(context: Context): AuthService {
        return RetrofitClient.getClient(BASE_URL, context).create(AuthService::class.java)
    }

    fun getAccessService(context: Context): AccessService {
        return RetrofitClient.getClient(BASE_URL, context).create(AccessService::class.java)
    }
}