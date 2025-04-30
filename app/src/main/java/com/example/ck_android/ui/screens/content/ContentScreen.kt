package com.example.ck_android.ui.screens.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
            .padding(16.dp)
    ) {

        // Header
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
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                "Nội dung",
                modifier = Modifier.weight(0.6f),
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                "Hành động",
                modifier = Modifier.weight(0.2f),
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }


        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray.copy(alpha = 0.3f))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(sampleData) { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .background(Color.White, shape = MaterialTheme.shapes.medium)
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
                            containerColor = Color(0xFF6200EE),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Chọn", fontSize = 14.sp)
                    }
                }

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray.copy(alpha = 0.2f)
                )
            }
        }
    }
}
