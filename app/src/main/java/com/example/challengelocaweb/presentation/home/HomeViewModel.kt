package com.example.challengelocaweb.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.challengelocaweb.domain.useCases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel(){

    val news = newsUseCases.getNews(
        sources = listOf("google-news-br", "globo", "infoMoney", "techcrunch", "the-verge")
    ).cachedIn(viewModelScope)

}