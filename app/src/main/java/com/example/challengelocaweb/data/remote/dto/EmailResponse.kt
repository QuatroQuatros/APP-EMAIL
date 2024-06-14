package com.example.challengelocaweb.data.remote.dto

import com.example.challengelocaweb.domain.model.Email

data class EmailResponse(
    val emails: List<Email>,
    val status: String,
    val totalResults: Int
)