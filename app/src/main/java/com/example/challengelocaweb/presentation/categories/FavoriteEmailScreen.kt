package com.example.challengelocaweb.presentation.categories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.presentation.common.EmailsList
import com.example.challengelocaweb.presentation.common.TopBarWithSearchBar
import com.example.challengelocaweb.presentation.event.components.FloatingActionButton
import com.example.challengelocaweb.presentation.home.HomeViewModel
import com.example.challengelocaweb.presentation.nvgraph.Route
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavoriteEmailsScreen(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    val favoriteEmails by viewModel.favoriteEmails.collectAsState(initial = emptyList())
    val pager = Pager(PagingConfig(pageSize = 20)) {
        object : PagingSource<Int, Email>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Email> {
                return LoadResult.Page(
                    data = favoriteEmails,
                    prevKey = null,
                    nextKey = null
                )
            }

            override fun getRefreshKey(state: PagingState<Int, Email>): Int? {
                return state.anchorPosition
            }
        }
    }

    val (searchTerm, setSearchTerm) = remember { mutableStateOf("") }
    val lazyFavoriteEmails = pager.flow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoritos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                EmailsList(
                    modifier = Modifier.weight(1f),
                    emails = lazyFavoriteEmails,
                    searchTerm = "",
                    viewModel = viewModel,
                    onClick = { email ->
                        val emailJson = Json.encodeToString(email)
                        val route = Route.EmailDetailScreen.createRoute(emailJson)
                        navController.navigate(route)
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {  })
        }
    )
}