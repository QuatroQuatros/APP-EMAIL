package com.example.challengelocaweb.presentation.nvgraph


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.challengelocaweb.presentation.auth.AuthViewModel
import com.example.challengelocaweb.presentation.auth.LoginScreen
import com.example.challengelocaweb.presentation.event.CalendarScreen
import com.example.challengelocaweb.presentation.event.EventViewModel
import com.example.challengelocaweb.presentation.categories.CategoriesScreen
import com.example.challengelocaweb.presentation.email.FavoriteEmailsScreen
import com.example.challengelocaweb.presentation.email.SentEmailScreen
import com.example.challengelocaweb.presentation.email.SpamEmailsScreen
import com.example.challengelocaweb.presentation.home.HomeScreen
import com.example.challengelocaweb.presentation.home.HomeViewModel
import com.example.challengelocaweb.presentation.email.ReadEmailScreen
import com.example.challengelocaweb.presentation.email.WriteEmailScreen
import com.example.challengelocaweb.presentation.auth.SignUpScreen
import com.example.challengelocaweb.presentation.settings.SettingsScreen
import com.example.challengelocaweb.presentation.settings.SettingsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Route.HomeScreen.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = Route.SingUpScreen.route) {
            val authViewModel: AuthViewModel = hiltViewModel();
            SignUpScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(route = Route.LoginScreen.route) {
            val authViewModel: AuthViewModel = hiltViewModel();
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(route = Route.SettingsScreen.route) {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            SettingsScreen(navController = navController, viewModel = settingsViewModel)
        }

        composable(route = Route.HomeScreen.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(navController = navController, viewModel = homeViewModel)
        }

        composable(route = Route.CategoriesScreen.route) {
            CategoriesScreen(navController = navController)
        }
        composable(route = Route.FavoriteEmailsScreen.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            FavoriteEmailsScreen(navController = navController, viewModel = homeViewModel)

        }

        composable(route = Route.SentEmailsScreen.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            SentEmailScreen(navController = navController, viewModel = homeViewModel)

        }


        composable(route = Route.SpamEmailsScreen.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            SpamEmailsScreen(navController = navController, viewModel = homeViewModel)
        }

        composable(route = Route.EventsScreen.route) {
            val eventViewModel: EventViewModel = hiltViewModel()
            val events by eventViewModel.events.observeAsState(emptyList())
            CalendarScreen(
                events = events,
                navController = navController,
                viewModel = eventViewModel
            )
        }

        composable(
            route = Route.EmailDetailScreen.route,
            arguments = listOf(navArgument("emailId") { type = NavType.IntType }),
            deepLinks = listOf(navDeepLink { uriPattern = "android-app://androidx.navigation/readEmail/{emailId}" })
        ) { backStackEntry ->
            val emailId = backStackEntry.arguments?.getInt("emailId")
            val homeViewModel: HomeViewModel = hiltViewModel()
            emailId?.let {
                ReadEmailScreen(emailId = it, navController = navController, viewModel = homeViewModel)
            }
        }
        composable(route = Route.WriteEmailScreen.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            WriteEmailScreen(navController = navController, viewModel = homeViewModel)
        }
    }
}
