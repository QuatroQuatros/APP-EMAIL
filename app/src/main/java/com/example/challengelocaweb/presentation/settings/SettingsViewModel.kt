package com.example.challengelocaweb.presentation.settings

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengelocaweb.api.Endpoint
import com.example.challengelocaweb.api.RetrofitInstance
import com.example.challengelocaweb.domain.model.User
import com.example.challengelocaweb.domain.model.UserPreferences
import com.example.challengelocaweb.util.PreferencesHelper
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
class SettingsViewModel
@Inject constructor(
    private val preferencesHelper: PreferencesHelper,
    private val context: Context
): ViewModel(){

    private val _selectedTheme = MutableStateFlow("System Default")
    private val _selectedLanguage = MutableStateFlow("en")
    private val _userId = MutableStateFlow(0L)
    private val _userName = MutableStateFlow("")
    private val _userPhoto = MutableStateFlow("")

    val selectedTheme: StateFlow<String> = _selectedTheme
    val selectedLanguage: StateFlow<String> = _selectedLanguage
    val userId: StateFlow<Long> = _userId
    val userName: StateFlow<String> = _userName
    val userPhoto: StateFlow<String> = _userPhoto

    val api = RetrofitInstance.createService(Endpoint::class.java, context)
    init {
        loadUserPreference()
    }

    fun loadUserPreference() {
        viewModelScope.launch {
            val user = preferencesHelper.getUser(context)
            user?.let {
                _userId.value = it.id.toLong()
                _userName.value = it.name
                _userPhoto.value = it.photo
                _selectedTheme.value = it.preferences.theme
                _selectedLanguage.value = it.preferences.language
            }

        }
    }

    fun applyUserThemePreference(theme: String) {
        Log.d("tema recebido", theme)
        viewModelScope.launch {
            preferencesHelper.applyUserThemePreference(context, theme)
            _selectedTheme.value = theme
        }
    }

    fun updateUser(userId: Long, name: String) {

        val userJson = JsonObject().apply {
            addProperty("name", name)

            val preferencesJson = JsonObject().apply {
                addProperty("theme", selectedTheme.value)
                addProperty("language", selectedLanguage.value)
            }

            add("preferences", preferencesJson)
        }

        Log.d("payload", userJson.toString())

        api.updateUser(userId, userJson).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    Log.d("SettingsViewModel", "Response code: ${response.code()}")

                    val responseBody = response.body()
                    if (responseBody != null) {
                        val data = responseBody.getAsJsonObject("data")
                        val preferencesObject = data?.getAsJsonObject("preferences")

                        preferencesObject?.let {
                            val userPreferences = UserPreferences(
                                id = it.get("id")?.asInt ?: 0,
                                theme = it.get("theme")?.asString ?: "system_default",
                                language = it.get("language")?.asString ?: "en"
                            )

                            val user = User(
                                id = data.get("id")?.asInt ?: 0,
                                name = data.get("name")?.asString ?: "",
                                email = data.get("email")?.asString ?: "",
                                photo = data.get("photo")?.asString ?: "",
                                preferences = userPreferences
                            )

                            preferencesHelper.saveUser(context, user)

                            _userName.value = user.name
                        }
                    } else {
                        Log.e("SettingsViewModel", "Response body is null")
                    }
                } else {
                    Log.e("SettingsViewModel", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("SettingsViewModel", "Error updating user", t)
            }
        })
    }



}