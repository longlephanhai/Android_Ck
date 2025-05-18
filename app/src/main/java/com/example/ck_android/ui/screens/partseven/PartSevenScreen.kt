package com.example.ck_android.ui.screens.partseven

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
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
import com.example.ck_android.ui.screens.content.LightBlueBackground
import com.example.ck_android.ui.screens.content.PrimaryBlue

@Composable
fun PartSevenScreen(
    navController: NavController,
    partSevenViewModel: PartSevenViewModel,
    mainViewModel: MainViewModel
) {
    val uiState = partSevenViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        partSevenViewModel.getPartSevenTitle()
    }
    val data = uiState.value.data

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
                text = "Danh sách Part 7",
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

        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = PrimaryBlue.copy(alpha = 0.3f)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(data) { index, item ->
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
                            item.name,
                            modifier = Modifier.weight(0.6f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Button(
                            onClick = {
                                navController.navigate("part_seven_item/${item._id}")
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