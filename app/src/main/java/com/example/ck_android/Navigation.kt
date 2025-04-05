package com.example.ck_android

import android.widget.Toast
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.ck_android.ui.screens.home.HomeScreen
import com.example.ck_android.ui.screens.home.HomeViewModel
import com.example.ck_android.ui.screens.login.LoginScreen
import com.example.ck_android.ui.screens.login.LoginViewModel
import com.example.ck_android.ui.screens.start.StartScreen
import com.example.ck_android.ui.screens.start.StartViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Start : Screen("start")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    val mainState = mainViewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(mainState.value.error) {
        if (mainState.value.error != "") {
            Toast.makeText(context, mainState.value.error, Toast.LENGTH_LONG).show()
            mainViewModel.setError("")
        }
    }
    NavHost(navController = navController, startDestination = Screen.Start.route) {
        composable(Screen.Start.route) {
            StartScreen(
                navController,
                startViewModel = hiltViewModel<StartViewModel>(),
                mainViewModel
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                navController,
                homeViewModel = hiltViewModel<HomeViewModel>(),
                mainViewModel
            )
        }
        composable(Screen.Login.route){
            LoginScreen(
                navController,
                loginViewModel = hiltViewModel<LoginViewModel>(),
                mainViewModel
            )
        }
    }
}