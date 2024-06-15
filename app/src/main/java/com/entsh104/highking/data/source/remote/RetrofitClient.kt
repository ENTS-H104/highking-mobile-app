package com.entsh104.highking.data.source.remote

import com.entsh104.highking.BuildConfig
import ApiService
import android.content.Context
import com.entsh104.highking.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


object RetrofitClient {
    private const val BASE_URL = BuildConfig.BASE_URL
    private var apiService: ApiService? = null

    fun createInstance(context: Context): ApiService {
        if (apiService == null) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            // Load CAs from an InputStream
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val certificateInputStream = context.resources.openRawResource(R.raw.highking)
            val certificate: Certificate
            certificateInputStream.use {
                certificate = certificateFactory.generateCertificate(it)
            }

            // Create a KeyStore containing our trusted CAs
            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType).apply {
                load(null, null)
                setCertificateEntry("ca", certificate)
            }

            // Create a TrustManager that trusts the CAs in our KeyStore
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
                init(keyStore)
            }

            // Create an SSLContext that uses our TrustManager
            val sslContext = SSLContext.getInstance("TLS").apply {
                init(null, trustManagerFactory.trustManagers, null)
            }

            // Create an OkHttpClient that uses our SSLSocketFactory
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .sslSocketFactory(sslContext.socketFactory, trustManagerFactory.trustManagers[0] as X509TrustManager)
                .build()

            // Create a Retrofit instance using the custom OkHttpClient
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
