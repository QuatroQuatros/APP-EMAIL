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

    object EmailDetailScreen : Route("readEmail/{email}"){
        fun createRoute(emailJson: String) = "readEmail/${Uri.encode(emailJson)}"
    }
    object AppStartNavigationScreen : Route("appStartNavigationScreen")
    object WriteEmailScreen : Route("writeEmailScreen")

    //object NewsNavigation : Route("newsNavigation")


}