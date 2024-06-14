package com.example.challengelocaweb.presentation.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.challengelocaweb.R
import com.example.challengelocaweb.presentation.nvgraph.Route

@Composable
fun CustomNavigationBar(
    navController: NavHostController,
    selectedItems: String,
    onItemSelected: (String) -> Unit
) {

    NavigationBar(
        //modifier = Modifier,
        containerColor = colorResource(id = R.color.primary),
        contentColor = colorResource(id = R.color.ic_launcher_background)
    
    ) {
        NavigationBarItem(
            selected = selectedItems == "Categories",
            onClick = {
                onItemSelected("Categories")
                navController.navigate(Route.CategoriesScreen.route)
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorResource(id = R.color.white),
                selectedTextColor = colorResource(id = R.color.white),
                indicatorColor = colorResource(id = R.color.selected),
                unselectedIconColor = colorResource(id = R.color.white),
                unselectedTextColor = colorResource(id = R.color.white),
                disabledIconColor = colorResource(id = R.color.body),
                disabledTextColor = colorResource(id = R.color.body)
            ),
            label = { Text("Categories") },
            icon = {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_flag),
                    tint = colorResource(id = R.color.white),
                    contentDescription = "Home"
                )
            }
        )
        NavigationBarItem(
            selected = selectedItems == "Home",
            onClick = {
                onItemSelected("Home")
                navController.navigate(Route.HomeScreen.route)
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorResource(id = R.color.white),
                selectedTextColor = colorResource(id = R.color.white),
                indicatorColor = colorResource(id = R.color.selected),
                unselectedIconColor = colorResource(id = R.color.white),
                unselectedTextColor = colorResource(id = R.color.white),
                disabledIconColor = colorResource(id = R.color.body),
                disabledTextColor = colorResource(id = R.color.body)
            ),
            label = { Text("Home") },
            icon = {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_email),
                    tint = colorResource(id = R.color.white),
                    contentDescription = "Home"
                )
            }
        )

        NavigationBarItem(
            selected = selectedItems == "Calendar",
            onClick = {
                onItemSelected("Calendar")
                navController.navigate(Route.CalendarScreen.route)
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorResource(id = R.color.white),
                selectedTextColor = colorResource(id = R.color.white),
                indicatorColor = colorResource(id = R.color.selected),
                unselectedIconColor = colorResource(id = R.color.white),
                unselectedTextColor = colorResource(id = R.color.white),
                disabledIconColor = colorResource(id = R.color.body),
                disabledTextColor = colorResource(id = R.color.body)
            ),
            label = { Text("Calendar") },
            icon = {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_calendar),
                    tint = colorResource(id = R.color.white),
                    contentDescription = "Home2"
                )
            })

    }
}
