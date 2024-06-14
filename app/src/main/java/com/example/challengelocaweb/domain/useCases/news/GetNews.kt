package com.example.challengelocaweb.domain.useCases.news

import androidx.paging.PagingData
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow


class GetNews(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(sources: List<String>): Flow<PagingData<Email>> {
        return newsRepository.getNews(sources)
    }
}