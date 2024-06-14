package com.example.challengelocaweb.presentation.nvgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.challengelocaweb.presentation.calendar.CalendarScreen
import com.example.challengelocaweb.presentation.home.HomeScreen
import com.example.challengelocaweb.presentation.home.HomeViewModel
import com.example.challengelocaweb.presentation.onBoarding.OnBoardScreen
import com.example.challengelocaweb.presentation.onBoarding.OnBoardingViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Route.AppStartNavigationScreen.route
){

    //val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        navigation(
            route = Route.AppStartNavigationScreen.route,
            startDestination = Route.HomeScreen.route
        ){
            composable(
                route =Route.HomeScreen.route
            ){
                val viewModel: HomeViewModel = hiltViewModel()
                val emails = viewModel.emailPagingDataFlow.collectAsLazyPagingItems()
                HomeScreen(navController = navController, emails = emails, navigate = {})
            }
        }

//        navigation(
//            route = Route.HomeScreen.route,
//            startDestination = Route.HomeScreen.route
//        ) {
//            composable(
//                route = Route.HomeScreen.route
//            ){
//                val viewModel: HomeViewModel = hiltViewModel()
//                //val emails = viewModel.news.collectAsLazyPagingItems()
//                val emails = viewModel.emailPagingDataFlow.collectAsLazyPagingItems()
//                HomeScreen(navController = navController, emails = emails, navigate = {})
//            }
//        }

        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.CalendarScreen.route
        ) {
            composable(
                route = Route.CalendarScreen.route
            ){
                CalendarScreen(navController = navController)
            }
        }





    }
}