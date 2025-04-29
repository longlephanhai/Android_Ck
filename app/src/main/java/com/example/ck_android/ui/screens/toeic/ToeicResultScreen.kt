package com.example.ck_android.ui.screens.toeic

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel

@Composable
fun ToeicResultScreen(
    navController: NavController,
    toeicResultViewModel: ToeicResultViewModel,
    mainViewModel: MainViewModel,
    id: String
) {
    val score = navController.previousBackStackEntry?.arguments?.getString("score") ?: "0"
    val correctAnswers =
        navController.previousBackStackEntry?.arguments?.getString("correctAnswers") ?: "[]"
    val userAnswers =
        navController.previousBackStackEntry?.arguments?.getString("userAnswers") ?: "{}"

    val correctAnswersList = correctAnswers
        .removeSurrounding("[", "]")
        .split(",")
        .map { it.trim() }
        .drop(1)

    val userAnswersMap = userAnswers
        .removeSurrounding("{", "}")
        .split(", ")
        .associate {
            val (key, value) = it.split("=")
            key.toInt() to value
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Kết quả bài thi",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Điểm của bạn: $score",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "Chi tiết đáp án:",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        correctAnswersList.forEachIndexed { index, correct ->
            val questionNumber = index + 1
            val user = userAnswersMap[questionNumber] ?: ""
            val isCorrect = correct == user

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Câu $questionNumber: Chọn $user",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = if (isCorrect) "✔ Đúng" else "❌ Sai (Đáp án: $correct)",
                    color = if (isCorrect) Color(0xFF4CAF50) else Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Quay lại")
        }
    }
}
