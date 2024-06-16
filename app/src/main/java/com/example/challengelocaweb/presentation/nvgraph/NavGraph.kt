package com.example.challengelocaweb.presentation.nvgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.challengelocaweb.presentation.event.CalendarScreen
import com.example.challengelocaweb.presentation.event.EventViewModel
import com.example.challengelocaweb.presentation.categories.CategoriesScreen
import com.example.challengelocaweb.presentation.home.HomeScreen
import com.example.challengelocaweb.presentation.home.HomeViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Route.AppStartNavigationScreen.route
){

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){

        navigation(
            route = Route.HomeScreen.route,
            startDestination = Route.CategoriesScreen.route
        ){
            composable(
                route =Route.CategoriesScreen.route
            ){
//                val viewModel: HomeViewModel = hiltViewModel()
//                val emails = viewModel.emailPagingDataFlow.collectAsLazyPagingItems()
                CategoriesScreen(navController = navController)
            }
        }

        navigation(
            route = Route.AppStartNavigationScreen.route,
            startDestination = Route.HomeScreen.route
        ){
            composable(
                route =Route.HomeScreen.route
            ){
                val homeViewModel: HomeViewModel = hiltViewModel()
                val emails = homeViewModel.emailPagingDataFlow.collectAsLazyPagingItems()



                HomeScreen(navController = navController, emails = emails, navigate = {})
            }
        }

        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.CalendarScreen.route
        ) {
            composable(
                route = Route.CalendarScreen.route
            ){
                val eventViewModel: EventViewModel = hiltViewModel()
                val events by eventViewModel.events.observeAsState(emptyList())
                CalendarScreen(
                    events = events,
                    navController = navController,
                    viewModel = eventViewModel
                )
            }
        }





    }
}