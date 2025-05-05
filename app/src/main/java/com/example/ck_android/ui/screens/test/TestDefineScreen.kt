package com.example.ck_android.ui.screens.test


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel
import com.example.ck_android.model.AnswerItem
import com.example.ck_android.model.TestDefineData

@Composable
fun TestDefineScreen(
    navController: NavController,
    testDefineViewModel: TestDefineViewModel,
    mainViewModel: MainViewModel,
    slug: String,
    category: String
) {
    val state = testDefineViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        testDefineViewModel.getVocabularyRandom(
            testDefineViewModel.getToken() ?: "",
            slug,
            category
        )
    }
    val vocabularyList = state.value.data
    QuizScreen(questions = vocabularyList, navController)
}

@Composable
fun QuizScreen(questions: List<TestDefineData>, navController: NavController) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<AnswerItem?>(null) }
    var showResult by remember { mutableStateOf(false) }
    var correctCount by remember { mutableStateOf(0) }

    if (currentQuestionIndex >= questions.size) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🎉 Hoàn thành!\nBạn trả lời đúng $correctCount / ${questions.size} câu",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF388E3C)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("🔙 Quay lại", color = Color.White)
            }
        }
        return
    }

    val question = questions[currentQuestionIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Câu ${currentQuestionIndex + 1} / ${questions.size}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Text(
                text = question.question,
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        question.answers.forEach { answer ->
            val isSelected = selectedAnswer?.text == answer.text
            val backgroundColor = when {
                showResult && isSelected && answer.isCorrect -> Color(0xFFA5D6A7)
                showResult && isSelected && !answer.isCorrect -> Color(0xFFEF9A9A)
                else -> Color(0xFFE0E0E0)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable(enabled = !showResult) {
                        selectedAnswer = answer
                        showResult = true
                        if (answer.isCorrect) correctCount++
                    },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = backgroundColor)
            ) {
                Text(
                    text = answer.text,
                    modifier = Modifier.padding(14.dp),
                    fontSize = 16.sp
                )
            }
        }

        if (showResult) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (selectedAnswer?.isCorrect == true) "✅ Chính xác!" else "❌ Sai rồi!",
                color = if (selectedAnswer?.isCorrect == true) Color(0xFF2E7D32) else Color(
                    0xFFC62828
                ),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    selectedAnswer = null
                    showResult = false
                    currentQuestionIndex++
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Câu tiếp theo")
            }
        }
    }
}
