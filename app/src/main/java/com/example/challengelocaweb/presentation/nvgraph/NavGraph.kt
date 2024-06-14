package com.example.challengelocaweb.presentation.nvgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.challengelocaweb.presentation.home.HomeScreen
import com.example.challengelocaweb.presentation.home.HomeViewModel
import com.example.challengelocaweb.presentation.onBoarding.OnBoardScreen
import com.example.challengelocaweb.presentation.onBoarding.OnBoardingViewModel

@Composable
fun NavGraph(
    startDestination: String
){

    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        navigation(
            route = Route.AppStartNavigationScreen.route,
            startDestination = Route.OnBoardingScreen.route
        ){
            composable(
                route =Route.OnBoardingScreen.route
            ){
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardScreen(
                    event = viewModel::onEvent
                )
            }
        }

        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigationScreen.route
        ) {
            composable(
                route = Route.NewsNavigationScreen.route
            ){
                val viewModel: HomeViewModel = hiltViewModel()
                //val emails = viewModel.news.collectAsLazyPagingItems()
                val emails = viewModel.emailPagingDataFlow.collectAsLazyPagingItems()
                HomeScreen(emails = emails, navigate = {})
            }
        }





    }
}