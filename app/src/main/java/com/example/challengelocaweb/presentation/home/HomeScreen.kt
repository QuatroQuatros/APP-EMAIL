package com.example.challengelocaweb.presentation.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.challengelocaweb.presentation.email.EmailsList
import com.example.challengelocaweb.presentation.common.TopBarWithSearchBar
import com.example.challengelocaweb.presentation.event.components.FloatingActionButton
import com.example.challengelocaweb.presentation.nvgraph.Route
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    val (searchTerm, setSearchTerm) = remember { mutableStateOf("") }

    val emails = viewModel.emailPagingDataFlow.collectAsLazyPagingItems()


    Scaffold(
        topBar = {
            TopBarWithSearchBar(
                text = searchTerm,
                onValueChange = setSearchTerm,
                onSearch = { }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(bottom = 90.dp)
            ) {
                EmailsList(
                    modifier = Modifier.weight(1f),
                    emails = emails,
                    searchTerm = searchTerm,
                    viewModel = viewModel,
                    onClick = {email->
                        val emailJson = Json.encodeToString(email)
                        val route = Route.EmailDetailScreen.createRoute(emailJson)
                        viewModel.markAsRead(email.id)
                        navController.navigate(route)
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Route.WriteEmailScreen.route)
                }
            )
        }
    )
}
