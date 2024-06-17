package com.example.challengelocaweb.domain.useCases.emails

import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.repository.EmailRepository
import kotlinx.coroutines.flow.Flow

class GetUnreadEmailCount(
    private val emailRepository: EmailRepository
) {

     operator fun invoke(): Flow<Int> {
        return emailRepository.getUnreadEmailCount()
    }

}