package com.example.challengelocaweb.presentation.nvgraph

import android.net.Uri

sealed class Route(
    val route: String
) {
    object HomeScreen : Route("homeScreen")

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