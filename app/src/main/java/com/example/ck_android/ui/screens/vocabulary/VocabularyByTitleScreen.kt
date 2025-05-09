package com.example.ck_android.ui.screens.vocabulary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.ck_android.MainViewModel
import com.example.ck_android.Screen
import com.example.ck_android.ui.screens.content.LightBlueBackground
import com.example.ck_android.ui.screens.content.PrimaryBlue

@Composable
fun VocabularyByTitleScreen(
    navController: NavController,
    vocabularyByTitleViewModel: VocabularyByTitleViewModel,
    mainViewModel: MainViewModel,
    slug: String
) {
    val state = vocabularyByTitleViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vocabularyByTitleViewModel.getVocabularyByTitle(
            vocabularyByTitleViewModel.getToken() ?: "",
            slug
        )
    }

    val vocabularyList = state.value.data

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlueBackground)
            .padding(16.dp)
    ) {
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

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(vocabularyList) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(16.dp)
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
                                item,
                                modifier = Modifier.weight(0.6f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    val category = item
                                    navController.navigate(
                                        Screen.VocabularyCategory.route
                                            .replace("{slug}", slug)
                                            .replace("{category}", category)
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF4CAF50),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Học", fontSize = 14.sp)
                            }

                            Button(
                                onClick = {
                                    val category = item
                                    navController.navigate(
                                        Screen.Test.route
                                            .replace("{slug}", slug)
                                            .replace("{category}", category)
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF2196F3),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Ôn tập", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }

        }
    }
}
