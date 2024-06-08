package com.entsh104.highking.data.model

data class TokenResponse(val token: String)

data class BasicResponse(val message: String)

data class UserResponse(
    val user_uid: String,
    val verified_status_uuid: String,
    val role: String,
    val email: String,
    val username: String,
    val image_url: String,
    val phone_number: String,
    val created_at: String,
    val updated_at: String
)
