package com.example.challengelocaweb

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.example.challengelocaweb.presentation.common.CustomNavigationBar
import com.example.challengelocaweb.presentation.nvgraph.NavGraph
import com.example.challengelocaweb.ui.theme.ChallengeLocaWebTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }


        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.splashCondition
            }
        }

        setContent {
            ChallengeLocaWebTheme {
                MyApp(viewModel)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp(viewModel: MainViewModel) {
    val isSystemInDarkMode = isSystemInDarkTheme()
    val systemController = rememberSystemUiController()

    SideEffect {
        systemController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !isSystemInDarkMode
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        var selectedItems by remember { mutableStateOf("Home") }

        Scaffold(
//            topBar = {
//
//                TopBarWithSearchBar(
//
//                )
//
//
//            },
            bottomBar = {
                CustomNavigationBar(
                    navController = navController,
                    selectedItems = selectedItems,
                    onItemSelected = { selectedItems = it }
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                //.padding(innerPadding)
                .background(color = MaterialTheme.colorScheme.background)) {
                NavGraph(navController = navController)
            }
        }
    }
}

