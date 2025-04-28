package com.example.ck_android.ui.screens.vocabulary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel
import com.example.ck_android.Screen
import com.example.ck_android.ui.screens.speakAI.SpeakAIViewModel

@Composable
fun VocabularyScreen(
    navController: NavController,
    vocabularyViewModel: VocabularyViewModel,
    mainViewModel: MainViewModel
) {
    val state=vocabularyViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vocabularyViewModel.getVocabularyTitle(vocabularyViewModel.getToken() ?: "")
    }
    val titleVocabularyList=state.value.data.titles

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

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
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                "Tiêu đề",
                modifier = Modifier.weight(0.6f),
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                "Thao tác",
                modifier = Modifier.weight(0.2f),
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray.copy(alpha = 0.3f))


        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(titleVocabularyList) { index, item ->
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
                        item._id,
                        modifier = Modifier.weight(0.6f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Button(
                        onClick = {
                           val slug=item.slug
                            navController.navigate(Screen.VocabularyByTitle.route.replace("{slug}", slug))
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