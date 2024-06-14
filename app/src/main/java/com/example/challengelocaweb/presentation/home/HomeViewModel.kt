package com.example.challengelocaweb.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.challengelocaweb.data.remote.MockEmailPagingSource
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.useCases.emails.EmailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val emailUseCases: EmailUseCases
): ViewModel(){

//    val news = emailUseCases.getEmails(
//        sources = listOf("google-news-br", "globo", "infoMoney", "techcrunch", "the-verge")
//    ).cachedIn(viewModelScope)

    val emailPagingDataFlow: Flow<PagingData<Email>> = Pager(PagingConfig(pageSize = 10)) {
        MockEmailPagingSource()
    }.flow.cachedIn(viewModelScope)

}