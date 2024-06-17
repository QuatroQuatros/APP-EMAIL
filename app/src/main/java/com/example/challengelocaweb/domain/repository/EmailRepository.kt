package com.example.challengelocaweb.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.challengelocaweb.domain.model.Email
import kotlinx.coroutines.flow.Flow


interface EmailRepository {

    suspend fun getEmails(): List<Email>
    suspend fun insert(email: Email)
    suspend fun deleteEmail(email: Email)
    suspend fun updateEmail(email: Email)
    fun getFavoritesEmails(): Flow<List<Email>>
    fun getSpamEmails(): Flow<List<Email>>
    suspend fun markAsRead(id: Int)
    suspend fun markAsNotSpam(id: Int)
    suspend fun markAsUnread(id: Int)
    suspend fun markAsSpam(id: Int)
    suspend fun markAsSecure(id: Int)
    fun getUnreadEmailCount(): Flow<Int>

}