package com.example.challengelocaweb.domain.useCases.emails

import com.example.challengelocaweb.domain.model.EmailWithAttachments
import com.example.challengelocaweb.domain.repository.EmailRepository
import kotlinx.coroutines.flow.Flow

class GetEmailsWithAttachments (
    private val emailRepository: EmailRepository
) {

    operator fun invoke(id: Int): Flow<EmailWithAttachments> {
        return emailRepository.getEmailsWithAttachments(id)
    }

}