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
//    suspend fun getEmails(): PagingSource<Int, Email>
//
//    suspend fun getEmailsList(): List<Email>
}