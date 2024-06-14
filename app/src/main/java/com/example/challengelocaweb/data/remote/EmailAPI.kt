package com.example.challengelocaweb.data.remote

import com.example.challengelocaweb.data.remote.dto.EmailResponse
import com.example.challengelocaweb.util.Constansts.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface EmailAPI {

    @GET("everything")
    suspend fun getEmails(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): EmailResponse
}