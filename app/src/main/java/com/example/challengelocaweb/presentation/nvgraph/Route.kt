package com.example.challengelocaweb.presentation.nvgraph

sealed class Route(
    val route: String
) {
    object OnBoardingScreen : Route("onBoardingScreen")
    object HomeScreen : Route("homeScreen")

    object CalendarScreen : Route("calendarScreen")




    object SearchScreen : Route("searchScreen")
    object BookmarkScreen : Route("bookmarkScreen")
    object DetailScreen : Route("detailScreen")
    object AppStartNavigationScreen : Route("appStartNavigationScreen")
    object NewsNavigation : Route("newsNavigation")


}