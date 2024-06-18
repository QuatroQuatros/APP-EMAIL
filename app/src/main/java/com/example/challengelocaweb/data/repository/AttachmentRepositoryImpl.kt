package com.example.challengelocaweb.data.repository

import com.example.challengelocaweb.domain.dao.AttachmentDao
import com.example.challengelocaweb.domain.model.Attachment
import com.example.challengelocaweb.domain.repository.AttachmentRepository
import javax.inject.Inject

class AttachmentRepositoryImpl @Inject constructor(
    private val attachmentDao: AttachmentDao
) : AttachmentRepository {

    override suspend fun getAttachmentsForEmail(emailId: Int): List<Attachment> {
        return attachmentDao.getAttachmentsForEmail(emailId)
    }

    override suspend fun insertAll(attachments: List<Attachment>) {
        attachmentDao.insertAll(attachments)
    }

    override suspend fun deleteAttachmentsForEmail(emailId: Int) {
        attachmentDao.deleteAttachmentsForEmail(emailId)
    }
}