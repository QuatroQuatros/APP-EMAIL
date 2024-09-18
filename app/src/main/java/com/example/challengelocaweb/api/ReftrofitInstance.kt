package com.example.challengelocaweb.api

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.challengelocaweb.BuildConfig;
import com.example.challengelocaweb.util.TokenManager
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

//object RetrofitInstance {
//    private const val BASE_URL = BuildConfig.BASE_URL
//
//    val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    fun <T> createService(serviceClass: Class<T>): T {
//        return retrofit.create(serviceClass)
//    }
//}

object RetrofitInstance {
    private const val BASE_URL = BuildConfig.BASE_URL

    private fun createOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(TokenManager(context)))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun <T> createService(serviceClass: Class<T>, context: Context): T {
        val okHttpClient = createOkHttpClient(context)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(serviceClass)
    }

}
