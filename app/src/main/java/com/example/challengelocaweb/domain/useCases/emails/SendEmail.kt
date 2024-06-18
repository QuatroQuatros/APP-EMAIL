package com.example.challengelocaweb.domain.useCases.emails

import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.repository.EmailRepository


class SendEmail(
    private val emailRepository: EmailRepository
) {
    suspend operator fun invoke(email: Email): Long {
        return emailRepository.sendEmail(email = email )
    }

}