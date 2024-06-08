import com.entsh104.highking.data.model.BasicResponse
import com.entsh104.highking.data.model.LoginRequest
import com.entsh104.highking.data.model.RegisterRequest
import com.entsh104.highking.data.model.TokenResponse
import com.entsh104.highking.data.model.UserResponse
import com.entsh104.highking.data.source.local.SharedPreferencesManager

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

    suspend fun getCurrentUser(token: String): Result<UserResponse> {
        return try {
            val response = apiService.getCurrentUser("Bearer $token")
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
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

    fun saveToken(token: String) {
        prefs.saveToken(token)
    }

    fun getToken(): String? {
        return prefs.getToken()
    }
}
