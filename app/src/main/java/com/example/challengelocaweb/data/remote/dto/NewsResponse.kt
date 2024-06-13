package com.example.challengelocaweb.data.remote.dto

import com.example.challengelocaweb.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)