package com.example.challengelocaweb.presentation.nvgraph

import android.net.Uri

sealed class Route(
    val route: String
) {

    object LoginScreen : Route("loginScreen")
    object SingUpScreen : Route("singUpScreen")
    object HomeScreen : Route("homeScreen")

    object SettingsScreen : Route("settingsScreen")

    object EventsScreen : Route("eventsScreen")

    object CategoriesScreen : Route("categoriesScreen")

    object FavoriteEmailsScreen : Route("favoriteEmailScreen")

    object SpamEmailsScreen : Route("SpamEmailsScreen")

    object SentEmailsScreen: Route("SentEmailsScreen")

    object EmailDetailScreen : Route("readEmail/{emailId}"){
        fun createRoute(emailId: Int) = "readEmail/$emailId"
    }
    object AppStartNavigationScreen : Route("appStartNavigationScreen")
    object WriteEmailScreen : Route("writeEmailScreen")


}