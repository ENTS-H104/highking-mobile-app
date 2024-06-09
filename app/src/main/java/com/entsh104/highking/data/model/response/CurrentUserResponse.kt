package com.entsh104.highking.data.model.response

import com.google.gson.annotations.SerializedName

class CurrentUserResponse(

    @field:SerializedName("data")
    val data: List<DataItem>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int
)

class DataItem(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("image_url")
    val imageUrl: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("phone_number")
    val phoneNumber: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("user_uid")
    val userUid: String,

    @field:SerializedName("verified_status_uuid")
    val verifiedStatusUuid: String,

    @field:SerializedName("username")
    val username: String
)
