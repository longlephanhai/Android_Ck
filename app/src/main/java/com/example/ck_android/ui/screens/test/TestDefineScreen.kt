package com.example.ck_android.ui.screens.test


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel

@Composable
fun TestDefineScreen(
    navController: NavController,
    testDefineViewModel: TestDefineViewModel,
    mainViewModel: MainViewModel,
    slug: String,
    category: String
) {
    val uiState by testDefineViewModel.uiState.collectAsState()
    val vocabularyList = uiState.data
}