package com.example.challengelocaweb.data.repository

import com.example.challengelocaweb.domain.repository.EmailRepository
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.dao.EmailDao
import com.example.challengelocaweb.domain.model.EmailWithAttachments
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmailRepositoryImpl @Inject constructor(
    private val emailDao: EmailDao,
) : EmailRepository {

    override suspend fun getEmails(): List<Email> {
        return emailDao.getEmails()
    }

    override fun getEmailsWithAttachments(id: Int): Flow<EmailWithAttachments> {
       return emailDao.getEmailWithAttachments(id)
    }

    override fun getFavoritesEmails(): Flow<List<Email>>
    {
        return emailDao.getFavoritesEmails()
    }

    override fun getSpamEmails(): Flow<List<Email>>
    {
        return emailDao.getSpamEmails()
    }

    override fun getUnreadEmailCount(): Flow<Int> {
        return emailDao.getUnreadEmailCount()
    }


    override suspend fun insert(email: Email) {
        emailDao.insertAll(listOf(email))
    }

    override suspend fun deleteEmail(email: Email) {
        emailDao.deleteEmail(email)
    }

    override suspend fun updateEmail(email: Email) {
        emailDao.updateEmail(email)
    }

    override suspend fun markAsRead(id: Int) {
        emailDao.markAsRead(id)
    }

    override suspend fun markAsUnread(id: Int) {
        emailDao.markAsUnread(id)
    }

    override suspend fun markAsSpam(id: Int) {
        emailDao.markAsSpam(id)
    }

    override suspend fun markAsNotSpam(id: Int) {
        emailDao.markAsNotSpam(id)
    }

    override suspend fun markAsSecure(id: Int) {
        emailDao.markAsSecure(id)
    }



}
