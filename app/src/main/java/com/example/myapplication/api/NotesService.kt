package com.example.myapplication.api

import com.example.myapplication.model.Note
import retrofit2.Call
import retrofit2.http.GET

interface NotesService {
    @GET("/api/notes")
    fun getNotes(): Call<ArrayList<Note>>
}