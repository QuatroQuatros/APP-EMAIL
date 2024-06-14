package com.example.challengelocaweb.domain.repository

import androidx.paging.PagingData
import com.example.challengelocaweb.domain.model.Email
import kotlinx.coroutines.flow.Flow


interface EmailRepository {

    fun getEmails(sources: List<String>): Flow<PagingData<Email>>
}