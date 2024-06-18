package com.example.challengelocaweb.presentation.categories

import android.annotation.SuppressLint
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
import com.example.challengelocaweb.presentation.email.EmailsList
import com.example.challengelocaweb.presentation.event.components.FloatingActionButton
import com.example.challengelocaweb.presentation.home.HomeViewModel
import com.example.challengelocaweb.presentation.nvgraph.Route

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SentEmailScreen(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    val favoriteEmails by viewModel.getSendEmails.collectAsState(initial = emptyList())
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
                title = { Text("Enviados") },
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
                        val route = Route.EmailDetailScreen.createRoute(email.id)
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