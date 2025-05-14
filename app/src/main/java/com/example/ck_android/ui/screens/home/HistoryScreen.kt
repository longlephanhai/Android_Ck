package com.example.ck_android.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.ScoreDataList



@Composable
fun HistoryScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
) {
    val scoreState = homeViewModel.scoreList.collectAsState()
    LaunchedEffect(Unit) {
        homeViewModel.getScore()
    }

    val data = scoreState.value.data

    // Hiển thị Loading hoặc dữ liệu nếu có
    if (scoreState.value.status is LoadStatus.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF2196F3)) // Màu xanh dương cho vòng xoay loading
        }
    } else if (scoreState.value.status is LoadStatus.Success && data.isNotEmpty()) {
        // Nếu có dữ liệu, hiển thị trong LazyColumn
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(data.size) { index ->
                ScoreCard(
                    scoreRequest = data[index],
                    onRetry = { examId ->
                        navController.navigate("toeic/${examId}")
                    }
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No score data available", color = Color.Gray)
        }
    }
}

@Composable
fun ScoreCard(scoreRequest: ScoreDataList, onRetry: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Exam: ${scoreRequest.examId.name}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3)
            )
            Text(
                text = "Exam Description: ${scoreRequest.examId.description}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Score: ${scoreRequest.score}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Correct Answers: ${scoreRequest.correctAnswers}",
                fontSize = 14.sp,
                color = Color.Green
            )
            Text(
                text = "Incorrect Answers: ${scoreRequest.incorrectAnswers}",
                fontSize = 14.sp,
                color = Color.Red
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onRetry(scoreRequest.examId._id) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
            ) {
                Text(text = "Làm lại", color = Color.White)
            }
        }
    }
}

