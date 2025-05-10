package com.example.ck_android.ui.screens.Writing

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel
import com.example.ck_android.common.enum.LoadStatus

@Composable
fun WritingScreen(
    navController: NavController,
    writingViewModel: WritingViewModel,
    mainViewModel: MainViewModel,
) {
    val context = LocalContext.current
    val uiState = writingViewModel.uiState.collectAsState()
    val titleState = writingViewModel.titleGemini.collectAsState()

    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 48.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔙 Nút quay lại
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3),
                contentColor = Color.White
            )
        ) {
            Text("🔙 Quay lại", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = titleState.value.data.title + " ${titleState.value.data.hints}",
            onValueChange = { writingViewModel.updateTitle(it) },
            label = { Text("Đề bài", style = MaterialTheme.typography.bodyLarge) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = { writingViewModel.getTitle() },
            enabled = titleState.value.status !is LoadStatus.Loading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3),
                contentColor = Color.White
            )
        ) {
            Text("🎲 Tạo đề ngẫu nhiên")
        }

        Spacer(modifier = Modifier.height(20.dp))


        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Viết bài tại đây") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            maxLines = 20
        )

        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = { writingViewModel.postPrompt(content) },
            enabled = uiState.value.status !is LoadStatus.Loading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("📤 Nộp bài viết")
        }

        Spacer(modifier = Modifier.height(24.dp))


        if (uiState.value.data.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE3F2FD)
                ),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🎯 Phản hồi từ AI:",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF0D47A1)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.value.data,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }
            }
        }



        if (uiState.value.status is LoadStatus.Loading || titleState.value.status is LoadStatus.Loading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
    }
}
