package com.entsh104.highking.data.model

data class LoginRequest(val email: String, val password: String)

data class RegisterRequest(val email: String, val username: String, val phone_number: String, val password: String)
