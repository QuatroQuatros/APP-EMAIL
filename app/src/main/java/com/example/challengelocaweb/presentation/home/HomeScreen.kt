package com.example.challengelocaweb.presentation.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.paging.compose.LazyPagingItems
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.challengelocaweb.R
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.presentation.calendar.CalendarScreen
import com.example.challengelocaweb.presentation.common.EmailsList
import com.example.challengelocaweb.presentation.common.TopBarWithSearchBar
import com.example.challengelocaweb.presentation.nvgraph.Route


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavHostController,
    emails: LazyPagingItems<Email>,
    navigate:(String) -> Unit,

) {

    val (searchTerm, setSearchTerm) = remember { mutableStateOf("") }
    var showModal by remember { mutableStateOf(false) }



    Scaffold(
        topBar = {
            TopBarWithSearchBar(
                text = searchTerm,
                onValueChange = setSearchTerm,
                onSearch = {
                }
            )
        }

    ) {
        Column {

            TopBarWithSearchBar(
                modifier = Modifier.weight(1f),
                text = searchTerm,
                onValueChange = setSearchTerm,
                onSearch = {
                }
            )

            EmailsList(
                modifier = Modifier.weight(5f),
                emails = emails,
                searchTerm = searchTerm,
                onClick = {
                    navigate(Route.DetailScreen.route)
                }
            )
        }
    }
}

