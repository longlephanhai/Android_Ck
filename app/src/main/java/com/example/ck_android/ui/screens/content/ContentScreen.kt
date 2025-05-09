package com.example.ck_android.ui.screens.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel
import com.example.ck_android.Screen

val PrimaryBlue = Color(0xFF1976D2)
val LightBlueBackground = Color(0xFFE3F2FD)

@Composable
fun ContentScreen(
    navController: NavController,
    contentViewModel: ContentViewModel,
    mainViewModel: MainViewModel
) {
    val sampleData = listOf(
        "Ngữ pháp cơ bản" to Screen.Grammar.route,
        "Từ vựng theo chủ đề" to Screen.Vocabulary.route,
        "Luyện thi Toeic online" to Screen.Toeic.route,
        "Luyện viết với AI" to Screen.Writing.route,
        "Luyện nói với AI" to Screen.SpeakAi.route
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlueBackground)
            .padding(16.dp)
    ) {
        // Nút quay lại
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Quay lại",
                    tint = PrimaryBlue
                )
            }
            Text(
                text = "Chọn nội dung học",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            )
        }

        // Header bảng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "STT",
                modifier = Modifier.weight(0.2f),
                fontSize = 16.sp,
                color = PrimaryBlue,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                "Nội dung",
                modifier = Modifier.weight(0.6f),
                fontSize = 16.sp,
                color = PrimaryBlue,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                "Thao tác",
                modifier = Modifier.weight(0.2f),
                fontSize = 16.sp,
                color = PrimaryBlue,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = PrimaryBlue.copy(alpha = 0.3f)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(sampleData) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "${index + 1}",
                            modifier = Modifier.weight(0.2f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            item.first,
                            modifier = Modifier.weight(0.6f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Button(
                            onClick = {
                                navController.navigate(item.second)
                            },
                            modifier = Modifier
                                .weight(0.2f)
                                .height(40.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryBlue,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Chọn",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }
        }
    }
}

