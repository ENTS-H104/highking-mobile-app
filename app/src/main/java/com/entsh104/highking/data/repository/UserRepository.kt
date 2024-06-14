import com.entsh104.highking.data.model.BasicResponse
import com.entsh104.highking.data.model.LoginRequest
import com.entsh104.highking.data.model.MitraProfileResponse
import com.entsh104.highking.data.model.MountainDetailResponse
import com.entsh104.highking.data.model.MountainResponse
import com.entsh104.highking.data.model.OpenTripDetailResponse
import com.entsh104.highking.data.model.OpenTripResponse
import com.entsh104.highking.data.model.RegisterRequest
import com.entsh104.highking.data.model.ResetPasswordRequest
import com.entsh104.highking.data.model.SearchOpenTripResponse
import com.entsh104.highking.data.model.TokenResponse
import com.entsh104.highking.data.model.TripFilter
import com.entsh104.highking.data.model.UserResponse
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    suspend fun resetPasswordUser(email: String): Result<BasicResponse> {
        return try {
            val response = apiService.forgotPassword(ResetPasswordRequest(email))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception){
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

    private var currentUser: UserResponse? = null
    suspend fun getCurrentUser(): Result<UserResponse> {
        val token = prefs.getToken()
        return if (token != null) {
            try {
                val response = apiService.getCurrentUser("Bearer $token")
                if (response.isSuccessful) {
                    val userApiResponse = response.body()
                    if (userApiResponse != null && userApiResponse.data.isNotEmpty()) {
                        currentUser = userApiResponse.data[0]
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

    fun getCurrentUserId(): String? {
        return currentUser?.user_uid
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

    suspend fun getOpenTrips(): Result<OpenTripResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getOpenTrips()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getOpenTripById(tripId: String): Result<OpenTripDetailResponse> {
        return try {
            val response = apiService.getOpenTripById(tripId)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to load trip details"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchOpenTrip(mountainId: String, date: String): Result<SearchOpenTripResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.searchOpenTrip(mountainId, date)
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception(response.message()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getMitraProfile(mitraId: String): Result<MitraProfileResponse> {
        return try {
            val response = apiService.getMitraProfile(mitraId)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMitraTrips(mitraId: String): Result<List<TripFilter>> {
        return try {
            val response = apiService.getMitraProfile(mitraId)
            if (response.isSuccessful) {
                val trips = response.body()?.data?.firstOrNull()?.open_trip_data ?: emptyList()
                Result.success(trips)
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
