package com.example.challengelocaweb.presentation.nvgraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.ActivityNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.challengelocaweb.presentation.home.HomeScreen
import com.example.challengelocaweb.presentation.home.HomeViewModel
import com.example.challengelocaweb.presentation.onBoarding.OnBoardScreen
import com.example.challengelocaweb.presentation.onBoarding.OnBoardingViewModel
import kotlinx.coroutines.flow.collectIndexed

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
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(articles = articles, navigate = {})
            }
        }





    }
}