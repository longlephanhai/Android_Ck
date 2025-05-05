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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
            .background(Color(0xFFF5F5F5)) // Màu nền dịu
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("STT", modifier = Modifier.weight(0.2f), fontSize = 14.sp)
            Text("Tiêu đề", modifier = Modifier.weight(0.6f), fontSize = 14.sp)
            Text("Thao tác", modifier = Modifier.weight(0.2f), fontSize = 14.sp)
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray.copy(alpha = 0.3f))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(vocabularyList) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "${index + 1}",
                                modifier = Modifier.weight(0.2f),
                                fontSize = 14.sp
                            )
                            Text(
                                item,
                                modifier = Modifier.weight(0.8f),
                                fontSize = 14.sp
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
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
