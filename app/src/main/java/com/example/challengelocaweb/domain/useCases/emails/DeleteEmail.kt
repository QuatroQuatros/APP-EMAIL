package com.example.challengelocaweb.domain.useCases.emails

import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.repository.EmailRepository

class DeleteEmail(private val repository: EmailRepository) {
    suspend operator fun invoke(email: Email) {
        repository.deleteEmail(email)
    }
}