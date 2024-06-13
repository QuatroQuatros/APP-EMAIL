package com.example.challengelocaweb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.challengelocaweb.domain.repository.NewsRepository
import com.example.challengelocaweb.data.remote.NewsAPI
import com.example.challengelocaweb.domain.model.Article
import com.example.challengelocaweb.data.remote.NewsPagingSource
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val newsApi: NewsAPI
) : NewsRepository {

    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { NewsPagingSource(
                newsAPI = newsApi,
                sources = sources.joinToString(separator = ",")) }
        ).flow
    }

}
