package com.example.challengelocaweb.domain.repository

import com.example.challengelocaweb.domain.model.Attachment

interface AttachmentRepository {
   suspend fun getAttachmentsForEmail(emailId: Int): List<Attachment>

    suspend fun insertAll(attachments: List<Attachment>)
    suspend fun deleteAttachmentsForEmail(emailId: Int)
}