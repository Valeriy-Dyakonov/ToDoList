package com.example.myapplication.api.retrofit

import com.example.myapplication.api.NotesService

object RestApiClient {
    private var BASE_URL = "http://10.0.2.2:8080/api/"

    val notesService: NotesService
        get() = RetrofitClient.getClient(BASE_URL).create(NotesService::class.java)
}