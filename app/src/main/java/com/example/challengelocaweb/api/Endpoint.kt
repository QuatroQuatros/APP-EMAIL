package com.example.challengelocaweb.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Endpoint {
    @GET("teste")
    fun getBatata(): Call<JsonObject>

    @POST("auth/register")
    fun register(@Body request: JsonObject): Call<JsonObject>

    @POST("auth/login")
    fun login(@Body request: JsonObject): Call<JsonObject>


    @POST("emails/send")
    fun sendEmail(@Body request: JsonObject): Call<JsonObject>

    @PUT("users/{id}")
    fun updateUser(@Path("id") userId: Long, @Body request: JsonObject): Call<JsonObject>

}