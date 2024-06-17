package com.example.challengelocaweb.domain.useCases.emails

import com.example.challengelocaweb.domain.repository.EmailRepository

class MarkAsNotSpam(private val repository: EmailRepository) {
    suspend operator fun invoke(id: Int) {
        repository.markAsNotSpam(id)
    }
}
