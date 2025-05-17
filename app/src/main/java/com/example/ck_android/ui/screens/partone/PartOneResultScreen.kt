package com.example.ck_android.ui.screens.partone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel

@Composable
fun PartOneResultScreen(
    navController: NavController,
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


    val userAnswersMap = userAnswers
        .removeSurrounding("{", "}")
        .split(", ")
        .associate {
            val (key, value) = it.split("=")
            key.toInt() to value
        }
    Text(text = "Số câu đúng: ${correctAnswersList.size}")
    Text(text = "Số câu đã trả lời: ${userAnswersMap.size}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🎯 Kết quả",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Điểm của bạn",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "$score điểm",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "📘 Chi tiết đáp án:",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column {
            correctAnswersList.forEachIndexed { index, correct ->
                val questionNumber = index+1
                val user = userAnswersMap[questionNumber] ?: ""
                val isCorrect = correct == user

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCorrect) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Câu $questionNumber",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Bạn chọn: $user",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Text(
                            text = if (isCorrect) "✔ Đúng" else "❌ Sai\nĐáp án: $correct",
                            color = if (isCorrect) Color(0xFF388E3C) else Color.Red,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3),
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "🔙 Quay lại", style = MaterialTheme.typography.titleMedium)
        }
    }
}