package com.example.challengelocaweb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.challengelocaweb.domain.repository.EmailRepository
import com.example.challengelocaweb.data.remote.EmailAPI
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.data.remote.EmailPagingSource
import com.example.challengelocaweb.domain.dao.EmailDao
import com.example.challengelocaweb.domain.model.Event
import com.example.challengelocaweb.util.NetworkUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmailRepositoryImpl @Inject constructor(
    private val emailDao: EmailDao,
    private val networkUtils: NetworkUtils
) : EmailRepository {

    override suspend fun getEmails(): List<Email> {
        return emailDao.getEmails()
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

    override fun getFavoritesEmails(): Flow<List<Email>>
    {
        return emailDao.getFavoritesEmails()
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

    override suspend fun markAsSecure(id: Int) {
        emailDao.markAsSecure(id)
    }


    override fun getUnreadEmailCount(): Flow<Int> {
        return emailDao.getUnreadEmailCount()
    }


}
