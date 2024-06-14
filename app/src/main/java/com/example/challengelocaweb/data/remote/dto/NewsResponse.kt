package com.example.challengelocaweb.data.remote.dto

import com.example.challengelocaweb.domain.model.Email

data class NewsResponse(
    val emails: List<Email>,
    val status: String,
    val totalResults: Int
)