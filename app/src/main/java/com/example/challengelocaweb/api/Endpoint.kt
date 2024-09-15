package com.example.challengelocaweb.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Endpoint {
    @GET("teste")
    fun getBatata(): Call<JsonObject>

    @POST("auth/register")
    fun register(@Body request: JsonObject): Call<JsonObject>

    @POST("auth/login")
    fun login(@Body request: JsonObject): Call<JsonObject>

}