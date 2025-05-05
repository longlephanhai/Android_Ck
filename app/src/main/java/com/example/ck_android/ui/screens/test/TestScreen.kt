package com.example.ck_android.ui.screens.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel
import com.example.ck_android.R

@Composable
fun TestScreen(
    navController: NavController,
    testViewModel: TestViewModel,
    mainViewModel: MainViewModel,
    slug: String,
    category: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TestCard(
            imageRes = R.drawable.flashcard,
            title = "Ôn tập cơ bản",
            onClick = {
                navController.navigate("TestFlashCard/$slug/$category")
            },
            modifier = Modifier.weight(0.3f)
        )

        TestCard(
            imageRes = R.drawable.match_define,
            title = "Nối các thẻ",
            onClick = {
//                navController.navigate("define/$slug/$category")
            },
            modifier = Modifier.weight(0.3f)
        )

        TestCard(
            imageRes = R.drawable.choose_define,
            title = "Chọn định nghĩa",
            onClick = {
//                navController.navigate("connect/$slug/$category")
            },
            modifier = Modifier.weight(0.3f)
        )
    }
}


@Composable
fun TestCard(
    imageRes: Int,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.Blue,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
