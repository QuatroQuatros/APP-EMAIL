package com.example.challengelocaweb

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.challengelocaweb.presentation.auth.AuthViewModel
import com.example.challengelocaweb.presentation.common.CustomNavigationBar
import com.example.challengelocaweb.presentation.home.HomeViewModel
import com.example.challengelocaweb.presentation.nvgraph.NavGraph
import com.example.challengelocaweb.presentation.nvgraph.Route
import com.example.challengelocaweb.ui.theme.ChallengeLocaWebTheme
import com.example.challengelocaweb.util.AppTheme
import com.example.challengelocaweb.util.LanguageChangeHelper
import com.example.challengelocaweb.util.PreferencesHelper
import com.example.challengelocaweb.util.ThemeSetting
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var themeSetting: ThemeSetting



    private val viewModel by viewModels<MainViewModel>()

    val languageChangeHelper by lazy { LanguageChangeHelper() }

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val savedTheme = sharedPreferences.getString("theme", "system_default")
        val savedLanguage = sharedPreferences.getString("language", "en")

        savedTheme?.let {
            if (savedLanguage != null) {
                languageChangeHelper.changeLanguage(this, savedLanguage)
            }
            preferencesHelper.applyUserPreferences(this, savedTheme, savedLanguage ?: "en")
            preferencesHelper.saveUserPreferences(this, savedTheme, savedLanguage ?: "en")
        }

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
            val theme = themeSetting.themeStream.collectAsState()
            val useDarkColors = when (theme.value) {
                AppTheme.MODE_AUTO -> isSystemInDarkTheme()
                AppTheme.MODE_DAY -> false
                AppTheme.MODE_NIGHT -> true
            }



            ChallengeLocaWebTheme(darkTheme = useDarkColors) {
                MyApp()
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val isSystemInDarkMode = isSystemInDarkTheme()
    val systemController = rememberSystemUiController()

    SideEffect {
        systemController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !isSystemInDarkMode
        )
    }

    val navController = rememberNavController()
    var selectedItems by rememberSaveable { mutableStateOf("Home") }
    val unreadCount by homeViewModel.unreadEmailCount.collectAsState(initial = 0)
    val isUserLoggedIn by authViewModel.isUserLoggedIn.collectAsState()

    var isBottomNavVisible by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val noBottomNavRoutes = listOf(Route.SingUpScreen.route, Route.LoginScreen.route)

    LaunchedEffect(isUserLoggedIn, currentRoute) {
        isBottomNavVisible = currentRoute !in noBottomNavRoutes
        delay(300)
    }

    Scaffold(
        bottomBar = {

            AnimatedVisibility(
                visible = isBottomNavVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                CustomNavigationBar(
                    navController = navController,
                    unreadCount = unreadCount,
                    selectedItems = selectedItems,
                    onItemSelected = { selectedItems = it }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            NavGraph(navController = navController, startDestination = if (isUserLoggedIn) Route.HomeScreen.route else Route.SingUpScreen.route)
        }
    }
}




