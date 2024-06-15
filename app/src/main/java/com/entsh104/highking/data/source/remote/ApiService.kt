import com.entsh104.highking.data.model.BasicResponse
import com.entsh104.highking.data.model.CreateTransactionRequest
import com.entsh104.highking.data.model.CreateTransactionResponse
import com.entsh104.highking.data.model.LoginRequest
import com.entsh104.highking.data.model.MitraProfileResponse
import com.entsh104.highking.data.model.MountainDetailResponse
import com.entsh104.highking.data.model.MountainsResponse
import com.entsh104.highking.data.model.OpenTripDetailResponse
import com.entsh104.highking.data.model.OpenTripResponse
import com.entsh104.highking.data.model.RegisterRequest
import com.entsh104.highking.data.model.ResetPasswordRequest
import com.entsh104.highking.data.model.SearchOpenTripResponse
import com.entsh104.highking.data.model.TokenResponse
import com.entsh104.highking.data.model.TransactionHistoryResponse
import com.entsh104.highking.data.model.UpdatePhotoUserRequest
import com.entsh104.highking.data.model.UpdateUserRequest
import com.entsh104.highking.data.model.UserApiResponse
import com.entsh104.highking.data.model.UserResponse
import com.entsh104.highking.data.model.UserUpdateApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("users/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<TokenResponse>

    @POST("users/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<BasicResponse>

    @POST("users/forgot-password")
    suspend fun forgotPassword(@Body request: ResetPasswordRequest): Response<BasicResponse>

    @PUT("users/update")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body request: UpdateUserRequest
    ): Response<UserUpdateApiResponse>

    @PUT("users/update/photo")
    suspend fun updatePhotoUser(
        @Header("Authorization") token: String,
        @Part("image_url") imageUrl: MultipartBody.Part
    ): Response<UserUpdateApiResponse>


    @GET("users/get-current-user")
    suspend fun getCurrentUser(@Header("Authorization") token: String): Response<UserApiResponse>

    @GET("users/logout")
    suspend fun logoutUser(@Header("Authorization") token: String): Response<BasicResponse>

    @GET("mountains")
    suspend fun getMountains(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 0
    ): Response<MountainsResponse>

    @GET("mountains/{id}")
    suspend fun getMountainById(@Path("id") id: String): Response<MountainDetailResponse>

    @GET("open-trips")
    suspend fun getOpenTrips(): Response<OpenTripResponse>

    @GET("open-trips/{open_trip_uuid}")
    suspend fun getOpenTripById(@Path("open_trip_uuid") openTripId: String): Response<OpenTripDetailResponse>

    @GET("search-ot")
    suspend fun searchOpenTrip(
        @Query("id") mountainId: String,
        @Query("date") date: String
    ): Response<SearchOpenTripResponse>

    @POST("transaction/create")
    suspend fun createTransaction(@Body request: CreateTransactionRequest): Response<CreateTransactionResponse>

    @GET("transaction/get-histories")
    suspend fun getTransactionHistories(
        @Query("id") id: String,
        @Query("status") status: String
    ): Response<TransactionHistoryResponse>

    @GET("open-trips/partners/{partner_uid}")
    suspend fun getMitraProfile(@Path("partner_uid") partnerUid: String): Response<MitraProfileResponse>

    @GET("open-trips/partners/{partner_uid}")
    suspend fun getMitraTrips(@Path("partner_uid") partnerUid: String): Response<MitraProfileResponse>

}
