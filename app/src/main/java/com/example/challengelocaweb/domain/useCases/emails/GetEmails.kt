package com.example.challengelocaweb.domain.useCases.emails

import androidx.paging.PagingData
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.repository.EmailRepository
import kotlinx.coroutines.flow.Flow


class GetEmails(
    private val emailRepository: EmailRepository
) {

    suspend operator fun invoke(): List<Email> {
        return emailRepository.getEmails()
    }

}