package com.example.challengelocaweb.presentation.home

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.challengelocaweb.api.Endpoint
import com.example.challengelocaweb.api.RetrofitInstance
import com.example.challengelocaweb.data.remote.EmailPagingSource
import com.example.challengelocaweb.domain.model.Attachment
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.model.EmailWithAttachments
import com.example.challengelocaweb.domain.model.SendEmail
import com.example.challengelocaweb.domain.useCases.emails.EmailUseCases
import com.example.challengelocaweb.util.TokenManager
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val emailPagingSource: EmailPagingSource,
    private val emailUseCases: EmailUseCases,
    private val context: Context
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

    val getSendEmails: Flow<List<Email>> = emailUseCases.getSendEmails()

    val draftEmails: Flow<List<Email>> = emailUseCases.draftEmails()


    val api = RetrofitInstance.createService(Endpoint::class.java, context)
    fun sendEmail(email: SendEmail, attachments: List<Uri>) {
        val emailJson = JsonObject().apply {
            addProperty("sender", email.sender)
            addProperty("subject", email.subject)
            addProperty("contentHtml", email.contentHtml)
            addProperty("contentPlain", email.contentPlain)
            addProperty("isConfidential", email.isConfidential)
        }

        // Se precisar adicionar os anexos, você pode modificar isso aqui
//        if (attachments.isNotEmpty()) {
//            val attachmentsJson = attachments.map { uri ->
//                JsonObject().apply {
//                    addProperty("filePath", uri.toString()) // Aqui estou apenas enviando o URI como exemplo
//                    addProperty("fileName", uri.lastPathSegment) // O nome do arquivo
//                }
//            }
//            emailJson.add("attachments", attachmentsJson)
//        }

        // Fazer a chamada da API para enviar o e-mail
        val call = api.sendEmail(emailJson)

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    // Email enviado com sucesso
                    Log.d("sendEmail", "Email enviado com sucesso: ${response.body()}")
                } else {
                    // Lidar com erro de envio
                    Log.e("sendEmail", "Erro ao enviar o email: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Lidar com falha de rede ou exceção durante a chamada
                Log.e("sendEmail", "Falha ao enviar o email: ${t.message}")
            }
        })
    }


//    fun sendEmail(email: Email, attachments: List<Uri>) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                val emailId = emailUseCases.sendEmail(email)
//                attachments.forEach {uri ->
//                    val attachment = Attachment(
//                        fileName = uri.lastPathSegment ?: "",
//                        filePath = uri.toString(),
//                        emailId = emailId.toInt()
//                    )
//                    emailUseCases.uploadFile(attachment)
//                }
//
//            }
//        }
//    }

    fun deleteEmail(email: Email) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                emailUseCases.deleteEmail(email)
            }
            refreshEmails()
        }
    }

    fun updateEmail(email: Email) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                emailUseCases.updateEmail(email)
            }
            refreshEmails()
        }
    }

    fun markAsRead(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                emailUseCases.markAsRead(id)
            }
            refreshEmails()
        }
    }

    fun markAsUnread(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                emailUseCases.markAsUnread(id)
            }
            refreshEmails()
        }
    }
    fun markAsSpam(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                emailUseCases.markAsSpam(id)
            }
            refreshEmails()
        }
    }

    fun markAsNotSpam(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                emailUseCases.markAsNotSpam(id)
            }
            refreshEmails()
        }
    }

    fun markAsSecure(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                emailUseCases.markAsSecure(id)
            }
            refreshEmails()
        }
    }

    fun getEmailWithAttachments(emailId: Int): Flow<EmailWithAttachments> {
        return emailUseCases.getEmailsWithAttachments(emailId)
    }

    private fun refreshEmails() {
        viewModelScope.launch {
            val updatedEmails = withContext(Dispatchers.IO) {
                emailUseCases.getEmails()
            }
            _emails.value = updatedEmails
            _refreshTrigger.value = Unit
        }
    }

//    private fun refreshEmails() {
//        viewModelScope.launch {
//            val updatedEmails = emailUseCases.getEmails()
//            _emails.value = updatedEmails
//            _refreshTrigger.value = Unit
//        }
//    }





}