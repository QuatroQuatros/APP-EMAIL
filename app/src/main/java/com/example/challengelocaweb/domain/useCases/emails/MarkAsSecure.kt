package com.example.challengelocaweb.domain.useCases.emails

import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.repository.EmailRepository

class MarkAsSecure(private val repository: EmailRepository) {
    suspend operator fun invoke(id: Int){
        repository.markAsSecure(id)
    }
}
