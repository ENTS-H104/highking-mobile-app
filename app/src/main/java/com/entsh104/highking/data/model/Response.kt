package com.entsh104.highking.data.model
import com.entsh104.highking.ui.model.Mountain
import com.google.gson.annotations.SerializedName

data class TokenResponse(val token: String)

data class BasicResponse(val message: String)

data class UserApiResponse(
    val status: Int,
    val message: String,
    val data: List<UserResponse>
)

data class UserResponse(
    val user_uid: String?,
    val verified_status_uuid: String?,
    val role: String?,
    val email: String?,
    val username: String?,
    val image_url: String?,
    val phone_number: String?,
    val created_at: String?,
    val updated_at: String?
)

data class MountainResponse(
    @SerializedName("mountain_uuid") val mountainId: String,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("description") val description: String,
    @SerializedName("height") val height: String,
    @SerializedName("status") val status: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("magmaCategory") val magmaCategory: String,
    @SerializedName("province") val province: String,
    @SerializedName("harga") val harga: Int,
    @SerializedName("gmaps") val gmaps: String,
    @SerializedName("total_trip_open") val totalTripOpen: Int
)

data class MountainsResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<MountainResponse>
)

data class MountainDetailResponse(
    @SerializedName("mountain_uuid") val mountainId: String,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("description") val description: String,
    @SerializedName("height") val height: String,
    @SerializedName("status") val status: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("magmaCategory") val magmaCategory: String,
    @SerializedName("province") val province: String,
    @SerializedName("harga") val harga: Int,
    @SerializedName("gmaps") val gmaps: String,
    @SerializedName("total_trip_open") val totalTripOpen: Int,
    @SerializedName("weather") val weather: Weather
)

data class Weather(
    @SerializedName("temperature") val temperature: String,
    @SerializedName("cuaca") val cuaca: String
)
