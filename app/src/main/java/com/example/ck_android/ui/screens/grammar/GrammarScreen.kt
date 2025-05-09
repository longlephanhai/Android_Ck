package com.example.ck_android.ui.screens.grammar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun GrammarScreen(
    navController: NavController,
    grammarViewModel: GrammarViewModel,
    mainViewModel: MainViewModel
) {
    val state = grammarViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        grammarViewModel.getGetAllGrammar(grammarViewModel.getToken() ?: "")
    }

    val grammarList = state.value.data

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlueBackground)
            .padding(16.dp)
    ) {
        // Nút quay lại và tiêu đề
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Quay lại",
                    tint = PrimaryBlue
                )
            }
            Text(
                text = "Danh sách ngữ pháp",
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
                "Tiêu đề",
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

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = PrimaryBlue.copy(alpha = 0.3f))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(grammarList) { index, item ->
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
                            item.title,
                            modifier = Modifier.weight(0.6f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Button(
                            onClick = {
                                navController.navigate(Screen.GrammarItem.route.replace("{slug}", item.slug))
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
                                contentDescription = "Chi tiết",
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

