package com.example.challengelocaweb.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ThemeSettingPreference @Inject constructor(
    @ApplicationContext context: Context
): ThemeSetting{

    override val themeStream: MutableStateFlow<AppTheme>
    override var theme: AppTheme by AppThemePreferenceDelegate("theme", AppTheme.MODE_AUTO)

    private val preferences: SharedPreferences

    init{
        preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        themeStream = MutableStateFlow(theme)
    }

    inner class AppThemePreferenceDelegate(
        private val name: String,
        private val default: AppTheme
    ): ReadWriteProperty<Any?, AppTheme> {
//        override fun getValue(thisRef: Any?, property: KProperty<*>): AppTheme =
//            AppTheme.fromOrdinal(preferences.getInt(name, default.ordinal))

        override fun getValue(thisRef: Any?, property: KProperty<*>): AppTheme {
            return when(preferences.getString(name, "system_default")){
                "light" -> AppTheme.fromOrdinal(AppTheme.MODE_DAY.ordinal)
                "dark" -> AppTheme.fromOrdinal(AppTheme.MODE_NIGHT.ordinal)
                "system_default" -> AppTheme.fromOrdinal(AppTheme.MODE_AUTO.ordinal)
                else -> AppTheme.fromOrdinal(AppTheme.MODE_AUTO.ordinal)
            }
        }



        override fun setValue(thisRef: Any?, property: KProperty<*>, value: AppTheme) {
            themeStream.value = value
            preferences.edit {
                when(value){
                    AppTheme.MODE_DAY -> putString(name, "light")
                    AppTheme.MODE_NIGHT -> putString(name, "dark")
                    AppTheme.MODE_AUTO -> putString(name, "system_default")
                }
                //putInt(name, value.ordinal)
            }
        }

    }

}