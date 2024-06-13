package com.example.challengelocaweb.data.remote

import com.example.challengelocaweb.data.remote.dto.NewsResponse
import com.example.challengelocaweb.util.Constansts.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}