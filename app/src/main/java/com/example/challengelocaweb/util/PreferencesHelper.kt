package com.example.challengelocaweb.util

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.platform.LocalContext
import com.example.challengelocaweb.domain.model.User
import com.example.challengelocaweb.domain.model.UserPreferences
import java.util.Locale
import javax.inject.Inject

class PreferencesHelper @Inject constructor(

){
    @Inject lateinit var themeSetting: ThemeSetting

    val languageChangeHelper by lazy { LanguageChangeHelper() }

    fun applyUserPreferences(context: Context, theme: String, language: String) {

        when (theme) {
            "light" -> themeSetting.theme = AppTheme.fromOrdinal(AppTheme.MODE_DAY.ordinal)
            "dark" -> themeSetting.theme = AppTheme.fromOrdinal(AppTheme.MODE_NIGHT.ordinal)
            "system_default" -> themeSetting.theme = AppTheme.fromOrdinal(AppTheme.MODE_AUTO.ordinal)
        }

        when (language) {
            "en" -> languageChangeHelper.changeLanguage(context, "en")
            "pt-BR" -> languageChangeHelper.changeLanguage(context, "pt-BR")
            "system_default" -> languageChangeHelper.changeLanguage(context, "en")
        }

    }

    fun applyUserThemePreference(context: Context, theme: String) {
        when (theme) {
            "light" -> themeSetting.theme = AppTheme.fromOrdinal(AppTheme.MODE_DAY.ordinal)
            "dark" -> themeSetting.theme = AppTheme.fromOrdinal(AppTheme.MODE_NIGHT.ordinal)
            "system_default" -> themeSetting.theme = AppTheme.fromOrdinal(AppTheme.MODE_AUTO.ordinal)
        }
        saveUserThemePreference(context, theme)
        context.setTheme(themeSetting.theme.ordinal)
    }

    fun saveUserThemePreference(context: Context, theme: String) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("theme", theme)
        editor.apply()
    }

    fun saveUserPreferences(context: Context, theme: String, language: String) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("theme", theme)
        editor.putString("language", language)
        editor.apply()
    }

    fun saveUser(context: Context, user: User) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("user_id", user.id)
        editor.putString("user_name", user.name)
        editor.putString("user_email", user.email)
        editor.putString("user_photo", user.photo)
        editor.putInt("preferences_id", user.preferences.id)
        editor.putString("theme", user.preferences.theme)
        editor.putString("language", user.preferences.language)

        editor.apply()
    }

    fun getUser(context: Context): User? {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        val id = sharedPreferences.getInt("user_id", -1)
        if (id == -1) return null

        val name = sharedPreferences.getString("user_name", "") ?: ""
        val email = sharedPreferences.getString("user_email", "") ?: ""
        val photo = sharedPreferences.getString("user_photo", "") ?: ""
        val preferencesId = sharedPreferences.getInt("preferences_id", -1)
        val theme = sharedPreferences.getString("theme", "system_default") ?: "system_default"
        val language = sharedPreferences.getString("language", "en") ?: "en"

        val preferences = UserPreferences(preferencesId, theme, language)

        return User(id, name, email, photo, preferences)
    }
}