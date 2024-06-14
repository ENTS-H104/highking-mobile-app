package com.entsh104.highking.data.model

data class LoginRequest(val email: String, val password: String)

data class ResetPasswordRequest(val email: String)

data class RegisterRequest(val email: String, val username: String, val phone_number: String, val password: String)

data class CreateTransactionRequest(
    val user_uid: String,
    val partner_uid: String,
    val open_trip_uuid: String,
    val total_participant: Int,
    val payment_gateway_uuid: String,
    val participants: List<Participant>
)

data class Participant(
    val name: String,
    val nik: String,
    val handphone_number: String
)


