package com.example.challengelocaweb

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengelocaweb.api.Endpoint
import com.example.challengelocaweb.util.TokenManager
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val api: Endpoint
) : ViewModel() {
    private val _registerSuccess = MutableStateFlow(false)

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    init {
        checkUserLoginStatus()
    }

    fun register(user: String, email: String, password: String) {
        Log.d("AuthViewModel", "Starting registration process")
        val requestBody = JsonObject().apply {
            addProperty("name", user)
            addProperty("email", email)
            addProperty("password", password)
        }

        api.register(requestBody).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val token = responseBody?.getAsJsonObject("data")?.get("token")?.asString
                    token?.let {
                        viewModelScope.launch {
                            saveUserToken(it)
                            _registerSuccess.value = true
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("AuthViewModel", "Error: ${t.message}")
            }
        })
    }

    fun login(email: String, password: String) {
        Log.d("AuthViewModel", "Starting login process")
        val requestBody = JsonObject().apply {
            addProperty("email", email)
            addProperty("password", password)
        }

        api.login(requestBody).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val token = responseBody?.getAsJsonObject("data")?.get("token")?.asString
                    token?.let {
                        viewModelScope.launch {
                            saveUserToken(it)
                            _registerSuccess.value = true
                        }
                    }
                } else {
                    Log.e("AuthViewModel", "Login failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("AuthViewModel", "Error: ${t.message}")
            }
        })
    }


    private fun checkUserLoginStatus() {
        viewModelScope.launch {
            val token = tokenManager.getToken()
            Log.d("AuthViewModel", "Retrieved token: $token")
            _isUserLoggedIn.value = token != null
        }
    }

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            tokenManager.saveToken(token)
            _isUserLoggedIn.value = true
        }
    }

    // Função para fazer logout e remover o token
    fun logout() {
        tokenManager.removeToken()
        _isUserLoggedIn.value = false
    }
    fun resetRegisterSuccess() {
        _registerSuccess.value = false
    }

}
