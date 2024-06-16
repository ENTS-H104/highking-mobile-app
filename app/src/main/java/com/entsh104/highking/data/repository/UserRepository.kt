import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
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
import com.entsh104.highking.data.model.UpdatePhotoResponse
import com.entsh104.highking.data.model.UpdateUserRequest
import com.entsh104.highking.data.model.UserResponse
import com.entsh104.highking.data.model.UserUpdateApiResponse
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class UserRepository(
    private val apiService: ApiService,
    private val prefs: SharedPreferencesManager
) {

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
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun editProfileUser(
        username: String,
        phone_number: String
    ): Result<UserUpdateApiResponse> {
        val token = prefs.getToken()
        return if (token != null) {
            try {
                val resp =
                    apiService.updateUser(
                        "Bearer $token",
                        UpdateUserRequest(username, phone_number)
                    )
                if (resp.isSuccessful) {
                    Result.success(resp.body()!!)
                } else {
                    Result.failure(Exception(resp.message()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(Exception("TOKEN Not Found"))
        }
    }
    suspend fun uploadPhoto(
        contentResolver: ContentResolver,
        cacheDir: File,
        imageUri: Uri
    ): Result<UpdatePhotoResponse> {
        val token = prefs.getToken() ?: return Result.failure(Exception("Token not found"))
        Log.d("UploadPhoto", "Token: $token")
        val parcelFileDescriptor = contentResolver.openFileDescriptor(imageUri, "r", null) ?: return Result.failure(Exception("Failed to open file descriptor"))
        Log.d("UploadPhoto", "File Descriptor opened successfully")
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(imageUri))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        return try {
            val response = apiService.updatePhotoUser("Bearer $token", body)
            Log.d("UploadPhoto", "Response: ${response.message()}")
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to upload photo: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("UploadPhoto", "Error: ${e.message}", e)
            Result.failure(e)
        }

    }


    fun ContentResolver.getFileName(uri: Uri): String {
        var name = ""
        val returnCursor = this.query(uri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

    suspend fun registerUser(
        email: String,
        username: String,
        phone_number: String,
        password: String
    ): Result<BasicResponse> {
        return try {
            val response =
                apiService.registerUser(RegisterRequest(email, username, phone_number, password))
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
//    suspend fun getMountains(): LiveData<PagingData<List<MountainResponse>>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5
//            ),
//            PagingSourceFactory = {
//                MountainPagingSource(apiService)
//            }
//        ).livedata
//    }

    suspend fun getMountainById(id: String): Result<MountainDetailResponse> {
        val token = prefs.getToken()
        return try {
            val response = apiService.getMountainById("Bearer $token", id)
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
        val token = prefs.getToken()
        return try {
            val response = apiService.getOpenTripById("Bearer $token", tripId)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to load trip details"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchOpenTrip(mountainId: String, date: String, date2: String): Result<SearchOpenTripResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.searchOpenTrip(mountainId, date, date2)
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
