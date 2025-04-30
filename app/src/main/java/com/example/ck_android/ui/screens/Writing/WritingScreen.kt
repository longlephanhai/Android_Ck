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
        modifier = Modifier.run {
            fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        }
    ) {
        // Hiển thị đề ngẫu nhiên
        OutlinedTextField(
            value = titleState.value.data.title+ "${titleState.value.data.hints}",
            onValueChange = {
                writingViewModel.updateTitle(it)
            },
            label = {
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.run { height(8.dp) })

        // Nút tạo đề
        Button(
            onClick = {
                writingViewModel.getTitle()
            },
            modifier = Modifier.align(Alignment.Start),
            enabled = titleState.value.status !is LoadStatus.Loading
        ) {
            Text("Tạo đề ngẫu nhiên")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Trường nhập bài viết
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Viết bài tại đây") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            maxLines = 20
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nút gửi bài viết
        Button(
            onClick = {
                writingViewModel.postPrompt(content)
            },
            enabled = uiState.value.status !is LoadStatus.Loading
        ) {
            Text("Nộp")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Kết quả phản hồi
        if (uiState.value.data.isNotEmpty()) {
            Text(uiState.value.data)
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (uiState.value.status is LoadStatus.Loading || titleState.value.status is LoadStatus.Loading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
    }
}
