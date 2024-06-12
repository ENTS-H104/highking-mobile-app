package com.entsh104.highking.data.source.remote

import ApiService
import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientPayment {
    private const val BASE_URL = "https://highking.daf2a.me/api/"
    private var apiService: ApiService? = null

    fun createInstance(context: Context): ApiService {
        if (apiService == null) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService!!
    }

    fun getInstance(): ApiService {
        return apiService ?: throw IllegalStateException("ApiService not initialized. Call createInstance(context) first.")
    }
}
