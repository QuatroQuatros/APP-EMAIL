package com.example.challengelocaweb.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val photo: String,
    val preferences: UserPreferences
)

