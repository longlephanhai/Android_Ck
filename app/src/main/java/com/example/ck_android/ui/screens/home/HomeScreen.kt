package com.example.ck_android.ui.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel
) {
    Text("Home Screen")
}