package com.example.ck_android.ui.screens.test

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel
import com.example.ck_android.model.TestQuizzData

@Composable
fun TestQuizzScreen(
    navController: NavController,
    testQuizzViewModel: TestQuizzViewModel,
    mainViewModel: MainViewModel,
    slug: String,
    category: String
) {
    val BlueMain = Color(0xFF1976D2)
    val BlueLight = Color(0xFFE3F2FD)
    val BlueDark = Color(0xFF0D47A1)

    val state = testQuizzViewModel.uiState.collectAsState()
    var shuffledWords by remember { mutableStateOf(listOf<TestQuizzData>()) }
    var shuffledMeanings by remember { mutableStateOf(listOf<TestQuizzData>()) }
    var selectedPairs by remember { mutableStateOf(listOf<SelectedItem>()) }
    var isComplete by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        testQuizzViewModel.getVocabularyRandomized(
            testQuizzViewModel.getToken() ?: "",
            slug,
            category
        )
    }

    val data = state.value.data

    LaunchedEffect(data) {
        if (data.isNotEmpty()) {
            shuffledWords = data.shuffled()
            shuffledMeanings = data.shuffled()
        }
    }

    fun handleSelect(item: TestQuizzData, type: String) {
        val updated = selectedPairs + SelectedItem(item, type)
        selectedPairs = updated

        if (updated.size == 2) {
            val first = updated[0]
            val second = updated[1]

            if (first.type != second.type && first.item._id == second.item._id) {
                shuffledWords = shuffledWords.filter { it._id != first.item._id }
                shuffledMeanings = shuffledMeanings.filter { it._id != first.item._id }
            }

            selectedPairs = emptyList()

            if (shuffledWords.isEmpty()) {
                isComplete = true
            }
        }
    }

    fun reset() {
        shuffledWords = data.shuffled()
        shuffledMeanings = data.shuffled()
        selectedPairs = emptyList()
        isComplete = false
    }

    Column(Modifier.padding(16.dp)) {
        Text(
            text = "🔵 Ghép Từ Với Nghĩa",
            style = MaterialTheme.typography.titleLarge,
            color = BlueDark
        )
        Spacer(Modifier.height(24.dp))

        Row {
            // Cột từ vựng
            Column(Modifier.weight(1f)) {
                shuffledWords.forEach { word ->
                    val isSelected =
                        selectedPairs.any { it.item._id == word._id && it.type == "word" }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .background(
                                if (isSelected) BlueMain else BlueLight,
                                shape = MaterialTheme.shapes.medium
                            )
                            .clickable { handleSelect(word, "word") }
                            .padding(12.dp)
                    ) {
                        Text(text = word.vocb, color = if (isSelected) Color.White else BlueDark)
                    }
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Cột nghĩa
            Column(Modifier.weight(1f)) {
                shuffledMeanings.forEach { meaning ->
                    val isSelected =
                        selectedPairs.any { it.item._id == meaning._id && it.type == "meaning" }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .background(
                                if (isSelected) BlueMain else BlueLight,
                                shape = MaterialTheme.shapes.medium
                            )
                            .clickable { handleSelect(meaning, "meaning") }
                            .padding(12.dp)
                    ) {
                        Text(
                            text = meaning.meaning,
                            color = if (isSelected) Color.White else BlueDark
                        )
                    }
                }
            }
        }

        if (isComplete) {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { reset() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🔁 Làm lại")
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("⬅️ Quay lại")
            }
        }
    }
}

data class SelectedItem(val item: TestQuizzData, val type: String)
