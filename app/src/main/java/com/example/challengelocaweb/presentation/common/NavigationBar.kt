package com.example.challengelocaweb.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
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
    unreadCount: Int,
    selectedItems: String,
    onItemSelected: (String) -> Unit
) {

    NavigationBar(
        //modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = colorResource(id = R.color.ic_launcher_background)
    
    ) {
        NavigationBarItem(
            selected = selectedItems == "Categorias",
            onClick = {
                onItemSelected("Categorias")
                navController.navigate(Route.CategoriesScreen.route)
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorResource(id = R.color.white),
                selectedTextColor = colorResource(id = R.color.white),
                indicatorColor = if (isSystemInDarkTheme()) colorResource(id = R.color.selected) else colorResource(id = R.color.selectedNav),
                unselectedIconColor = colorResource(id = R.color.white),
                unselectedTextColor = colorResource(id = R.color.white),
                disabledIconColor = colorResource(id = R.color.body),
                disabledTextColor = colorResource(id = R.color.body)
            ),
            label = { Text("Categorias") },
            icon = {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_bookmark),
                    tint = colorResource(id = R.color.white),
                    contentDescription = "Categorias"
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
                if (isSystemInDarkTheme()) colorResource(id = R.color.selected) else colorResource(id = R.color.selectedNav),
                unselectedIconColor = colorResource(id = R.color.white),
                unselectedTextColor = colorResource(id = R.color.white),
                disabledIconColor = colorResource(id = R.color.body),
                disabledTextColor = colorResource(id = R.color.body)
            ),
            label = { Text("Home") },
            icon = {
                if (unreadCount > 0) {
                    BadgedBox(
                        badge = {
                            Badge {
                                Text(
                                    text = unreadCount.toString(),
                                    color = colorResource(id = R.color.white)
                                )
                            }
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_email),
                            tint = colorResource(id = R.color.white),
                            contentDescription = "Home"
                        )
                    }
                } else {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_email),
                        tint = colorResource(id = R.color.white),
                        contentDescription = "Home"
                    )
                }
            }
        )

        NavigationBarItem(
            selected = selectedItems == "Calendario",
            onClick = {
                onItemSelected("Calendario")
                navController.navigate(Route.EventsScreen.route)
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorResource(id = R.color.white),
                selectedTextColor = colorResource(id = R.color.white),
                if (isSystemInDarkTheme()) colorResource(id = R.color.selected) else colorResource(id = R.color.selectedNav),
                unselectedIconColor = colorResource(id = R.color.white),
                unselectedTextColor = colorResource(id = R.color.white),
                disabledIconColor = colorResource(id = R.color.body),
                disabledTextColor = colorResource(id = R.color.body)
            ),
            label = { Text("Calendario") },
            icon = {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_calendar),
                    tint = colorResource(id = R.color.white),
                    contentDescription = "Calendario"
                )
            })

    }
}
