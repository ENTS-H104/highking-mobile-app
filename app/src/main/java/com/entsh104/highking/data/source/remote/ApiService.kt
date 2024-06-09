import com.entsh104.highking.data.model.BasicResponse
import com.entsh104.highking.data.model.LoginRequest
import com.entsh104.highking.data.model.MountainDetailResponse
import com.entsh104.highking.data.model.MountainsResponse
import com.entsh104.highking.data.model.OpenTripDetailResponse
import com.entsh104.highking.data.model.OpenTripResponse
import com.entsh104.highking.data.model.RegisterRequest
import com.entsh104.highking.data.model.SearchOpenTripResponse
import com.entsh104.highking.data.model.TokenResponse
import com.entsh104.highking.data.model.UserApiResponse
import com.entsh104.highking.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("users/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<TokenResponse>

    @POST("users/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<BasicResponse>

    @GET("users/get-current-user")
    suspend fun getCurrentUser(@Header("Authorization") token: String): Response<UserApiResponse>

    @GET("users/logout")
    suspend fun logoutUser(@Header("Authorization") token: String): Response<BasicResponse>

    @GET("mountains")
    suspend fun getMountains(): Response<MountainsResponse>

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

}
