package com.example.myapplication.model

import java.io.Serializable

class AuthRequest(val login: String, val password: String): Serializable {
}