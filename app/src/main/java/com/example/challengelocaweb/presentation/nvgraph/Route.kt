package com.example.challengelocaweb.presentation.nvgraph

import android.net.Uri

sealed class Route(
    val route: String
) {
    object HomeScreen : Route("homeScreen")

    object EventsScreen : Route("eventsScreen")

    object CategoriesScreen : Route("categoriesScreen")

    object SearchScreen : Route("searchScreen")
    object EmailDetailScreen : Route("readEmail/{email}"){
        fun createRoute(emailJson: String) = "readEmail/${Uri.encode(emailJson)}"
    }
    object AppStartNavigationScreen : Route("appStartNavigationScreen")
    object NewsNavigation : Route("newsNavigation")


}