package com.example.challengelocaweb.domain.useCases.emails

import com.example.challengelocaweb.domain.repository.EmailRepository

class MarkAsRead(private val repository: EmailRepository) {
    suspend operator fun invoke(id: Int) {
        repository.markAsRead(id)
    }
}

