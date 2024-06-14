package com.example.challengelocaweb.domain.useCases.emails

import androidx.paging.PagingData
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.repository.EmailRepository
import kotlinx.coroutines.flow.Flow


class GetEmails(
    private val emailRepository: EmailRepository
) {

    operator fun invoke(sources: List<String>): Flow<PagingData<Email>> {
        return emailRepository.getEmails(sources)
    }
}