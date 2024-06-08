import com.entsh104.highking.data.model.BasicResponse
import com.entsh104.highking.data.model.LoginRequest
import com.entsh104.highking.data.model.RegisterRequest
import com.entsh104.highking.data.model.TokenResponse
import com.entsh104.highking.data.model.UserApiResponse
import com.entsh104.highking.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("users/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<TokenResponse>

    @POST("users/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<BasicResponse>

    @GET("users/get-current-user")
    suspend fun getCurrentUser(@Header("Authorization") token: String): Response<UserApiResponse>

    @GET("users/logout")
    suspend fun logoutUser(@Header("Authorization") token: String): Response<BasicResponse>
}
