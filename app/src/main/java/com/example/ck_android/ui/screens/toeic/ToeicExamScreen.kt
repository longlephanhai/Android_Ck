package com.example.ck_android.ui.screens.toeic

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel

@Composable
fun ToeicExamScreen(
    navController: NavController,
    toeicExamViewModel: ToeicExamViewModel,
    mainViewModel: MainViewModel,
    id: String
) {
    Text(text = id)
}