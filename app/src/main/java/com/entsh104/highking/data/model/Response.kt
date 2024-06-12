package com.entsh104.highking.data.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
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

data class OpenTripResponse(
    val status: Int,
    val message: String,
    val data: List<TripFilter>
)

data class SearchOpenTripResponse(
    val status: Int,
    val message: String,
    val data: List<TripFilter>
)

@Parcelize
data class TripFilter(
    val open_trip_uuid: String,
    val name: String,
    val image_url: String,
    val price: Int,
    val mountain_name: String,
    val mountain_uuid: String,
    val total_participants: String?,
    val min_people: Int?,
    val max_people: Int?
) : Parcelable


data class OpenTripDetailResponse(
    val status: Int?,
    val message: String?,
    val data: List<OpenTripDetail>?
)

data class OpenTripDetail(
    val open_trip_uuid: String,
    val open_trip_name: String,
    val image_url: String,
    val description: String,
    val price: Int,
    val min_people: Int,
    val max_people: Int,
    val include: String,
    val exclude: String,
    val gmaps: String,
    val policy: String,
    val mountain_data: List<MountainData>,
    val mitra_data: List<MitraData>,
    val schedule_data: List<ScheduleData>,
    val rundown_data: List<RundownData>,
    val faq_data: List<FaqData>
)

data class MountainData(
    val mountain_uuid: String,
    val name: String,
    val image_url: String,
    val gmaps: String
)

data class MitraData(
    val partner_uid: String,
    val username: String,
    val image_url: String
)

data class MitraProfileResponse(
    val status: Int,
    val message: String,
    val data: List<MitraProfile>
)

@Parcelize
data class MitraProfile(
    val partner_uid: String,
    val partner_role_uuid: String,
    val verified_status_uuid: String,
    val email: String,
    val phone_number: String,
    val domicile_address: String,
    val username: String,
    val image_url: String,
    val created_at: String,
    val updated_at: String,
    val open_trip_data: List<TripFilter>
) : Parcelable

data class ScheduleData(
    val open_trip_schedule_uuid: String,
    val start_date: String,
    val end_date: String,
    val start_time: String,
    val end_time: String,
    val total_day: Int
)

data class RundownData(
    val day: Int,
    val description: String
)

data class FaqData(
    val description: String
)

data class CreateTransactionResponse(
    val message: String,
    val data: TransactionData
)

data class TransactionData(
    val transaction_logs_uuid: String,
    val user_uid: String,
    val open_trip_uuid: String,
    val status: String,
    val total_participant: Int,
    val amount_paid: Int,
    val participants: List<Participant>
)

data class TransactionHistoryResponse(
    val status: Int,
    val message: String,
    val data: List<TransactionHistory>
)

data class TransactionHistory(
    val transaction_logs_uuid: String,
    val user_uid: String,
    val open_trip_uuid: String,
    val payment_gateway_uuid: String,
    val status_accepted: String,
    val token: String?,
    val total_participant: Int,
    val amount_paid: Int,
    val status_payment: String,
    val updated_at: String,
    val created_at: String
)

data class PaymentLinkResponse(
    val url: String
)