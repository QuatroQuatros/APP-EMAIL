package com.example.challengelocaweb.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.challengelocaweb.data.remote.EmailPagingSource
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.model.EmailWithAttachments
import com.example.challengelocaweb.domain.useCases.emails.EmailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val emailPagingSource: EmailPagingSource,
    private val emailUseCases: EmailUseCases
): ViewModel(){

    private val _refreshTrigger = MutableStateFlow(Unit)

    @OptIn(ExperimentalCoroutinesApi::class)
    val emailPagingDataFlow: Flow<PagingData<Email>> = _refreshTrigger.flatMapLatest {
        Pager(PagingConfig(pageSize = 10)) {
            emailPagingSource
        }.flow.cachedIn(viewModelScope)
    }

    private val _emails = MutableStateFlow<List<Email>>(emptyList())
    val emails: StateFlow<List<Email>> = _emails.asStateFlow()


    private val _emailsWithAttachments = MutableStateFlow<List<EmailWithAttachments>>(emptyList())
    val emailsWithAttachments: StateFlow<List<EmailWithAttachments>> = _emailsWithAttachments.asStateFlow()



    init {
        refreshEmails()
    }

    val unreadEmailCount: Flow<Int> = emailUseCases.getUnreadEmailCount()

    val favoriteEmails: Flow<List<Email>> = emailUseCases.favoritesEmails()

    val spamEmails: Flow<List<Email>> = emailUseCases.spamEmails()

    fun deleteEmail(email: Email) {
        viewModelScope.launch {
            emailUseCases.deleteEmail(email)
            refreshEmails()
        }
    }

    fun updateEmail(email: Email) {
        viewModelScope.launch {
            emailUseCases.updateEmail(email)
            refreshEmails()
        }
    }

    fun markAsRead(id: Int) {
        viewModelScope.launch {
            emailUseCases.markAsRead(id)
            refreshEmails()
        }
    }

    fun markAsUnread(id: Int) {
        viewModelScope.launch {
            emailUseCases.markAsUnread(id)
            refreshEmails()
        }
    }
    fun markAsSpam(id: Int) {
        viewModelScope.launch {
            emailUseCases.markAsSpam(id)
            refreshEmails()
        }
    }

    fun markAsNotSpam(id: Int) {
        viewModelScope.launch {
            emailUseCases.markAsNotSpam(id)
            refreshEmails()
        }
    }

    fun markAsSecure(id: Int) {
        viewModelScope.launch {
            emailUseCases.markAsSecure(id)
            refreshEmails()
        }
    }


    private fun refreshEmails() {
        viewModelScope.launch {
            val updatedEmails = emailUseCases.getEmails()
            _emails.value = updatedEmails
            _refreshTrigger.value = Unit
        }
    }
    fun getEmailWithAttachments(emailId: Int): Flow<EmailWithAttachments> {
        return emailUseCases.getEmailsWithAttachments(emailId)
    }




}