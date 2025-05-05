package com.example.ck_android.ui.screens.test

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel

@Composable
fun TestQuizzSCreen(
    navController: NavController,
    testQuizzViewModel: TestQuizzViewModel,
    mainViewModel: MainViewModel,
    slug: String,
    category: String
) {
    Text(text = "Quizz Screen")
}