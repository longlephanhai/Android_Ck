package com.example.ck_android.ui.screens.grammar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel


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
            itemsIndexed(grammarList) { index, item ->
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
                        item.title,
                        modifier = Modifier.weight(0.6f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Button(
                        onClick = {

                            navController.navigate("grammarDetail/${item.slug}")
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
