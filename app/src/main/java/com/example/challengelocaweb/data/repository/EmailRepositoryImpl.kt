package com.example.challengelocaweb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.challengelocaweb.domain.repository.EmailRepository
import com.example.challengelocaweb.data.remote.EmailAPI
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.data.remote.EmailPagingSource
import kotlinx.coroutines.flow.Flow

class EmailRepositoryImpl(
    private val emailApi: EmailAPI
) : EmailRepository {


    override fun getEmails(sources: List<String>): Flow<PagingData<Email>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory ={
                EmailPagingSource(
                    emailAPI = emailApi,
                    sources = sources.joinToString(separator = ","),
                    searchTerm = ""
                )
            }

        ).flow
    }

}
