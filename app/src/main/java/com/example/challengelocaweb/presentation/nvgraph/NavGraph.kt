package com.example.challengelocaweb.presentation.nvgraph

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.presentation.event.CalendarScreen
import com.example.challengelocaweb.presentation.event.EventViewModel
import com.example.challengelocaweb.presentation.categories.CategoriesScreen
import com.example.challengelocaweb.presentation.home.HomeScreen
import com.example.challengelocaweb.presentation.home.HomeViewModel
import com.example.challengelocaweb.presentation.readEmail.ReadEmailScreen
import kotlinx.serialization.json.Json

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

        composable(route = Route.HomeScreen.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(navController = navController, viewModel = homeViewModel)
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
        composable(route = Route.CategoriesScreen.route) {
            CategoriesScreen(navController = navController)
        }
        composable(
            route = Route.EmailDetailScreen.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val emailJson = backStackEntry.arguments?.getString("email")
            val email = Email.fromJson(emailJson)
            var favoriteEmails by remember { mutableStateOf(listOf<Email>()) }
            emailJson?.let {
                val email = Json.decodeFromString<Email>(Uri.decode(it))
                val homeViewModel: HomeViewModel = hiltViewModel()
                ReadEmailScreen(
                    email = email,
                    navController = navController,
                    viewModel = homeViewModel,
//                    onFavoriteClick = {
//                        email.isFavorite = !email.isFavorite
//                        homeViewModel.updateEmail(email)
//                    }
                )
            }
        }
    }
}
