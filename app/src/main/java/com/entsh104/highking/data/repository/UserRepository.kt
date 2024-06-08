import com.entsh104.highking.data.model.BasicResponse
import com.entsh104.highking.data.model.LoginRequest
import com.entsh104.highking.data.model.MountainDetailResponse
import com.entsh104.highking.data.model.MountainResponse
import com.entsh104.highking.data.model.RegisterRequest
import com.entsh104.highking.data.model.TokenResponse
import com.entsh104.highking.data.model.UserResponse
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.ui.model.Mountain

class UserRepository(private val apiService: ApiService, private val prefs: SharedPreferencesManager) {

    suspend fun loginUser(email: String, password: String): Result<TokenResponse> {
        return try {
            val response = apiService.loginUser(LoginRequest(email, password))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registerUser(email: String, username: String, phone_number: String, password: String): Result<BasicResponse> {
        return try {
            val response = apiService.registerUser(RegisterRequest(email, username, phone_number, password))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCurrentUser(): Result<UserResponse> {
        val token = prefs.getToken()
        return if (token != null) {
            try {
                val response = apiService.getCurrentUser("Bearer $token")
                if (response.isSuccessful) {
                    val userApiResponse = response.body()
                    if (userApiResponse != null && userApiResponse.data.isNotEmpty()) {
                        Result.success(userApiResponse.data[0])
                    } else {
                        Result.failure(Exception("No user data found"))
                    }
                } else {
                    Result.failure(Exception(response.message()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(Exception("Token not found"))
        }
    }

    suspend fun logoutUser(token: String): Result<BasicResponse> {
        return try {
            val response = apiService.logoutUser("Bearer $token")
            if (response.isSuccessful) {
                prefs.clear()
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMountains(): Result<List<MountainResponse>> {
        return try {
            val response = apiService.getMountains()
            if (response.isSuccessful) {
                Result.success(response.body()?.data ?: emptyList())
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMountainById(id: String): Result<MountainDetailResponse> {
        return try {
            val response = apiService.getMountainById(id)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun saveToken(token: String) {
        prefs.saveToken(token)
    }

    fun getToken(): String? {
        return prefs.getToken()
    }
}
