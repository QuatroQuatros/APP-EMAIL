package com.example.challengelocaweb.domain.useCases.emails

import com.example.challengelocaweb.domain.model.Attachment
import com.example.challengelocaweb.domain.repository.EmailRepository

class UploadFile (
    private val emailRepository: EmailRepository
    ) {
    suspend operator fun invoke(attachment: Attachment) {
        return emailRepository.insertAttachment(attachment = attachment )
    }

}