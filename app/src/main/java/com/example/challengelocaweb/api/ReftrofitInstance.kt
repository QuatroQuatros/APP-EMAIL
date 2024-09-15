package com.example.challengelocaweb.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.challengelocaweb.BuildConfig;

object RetrofitInstance {
    private const val BASE_URL = BuildConfig.BASE_URL

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}
