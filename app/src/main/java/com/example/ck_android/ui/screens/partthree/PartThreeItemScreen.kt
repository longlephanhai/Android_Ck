package com.example.ck_android.ui.screens.partthree

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ck_android.MainViewModel
import com.example.ck_android.Screen
import com.example.ck_android.repositories.ApiClient.ApiConfig
import com.example.ck_android.ui.screens.content.LightBlueBackground
import com.example.ck_android.ui.screens.parttwo.AudioPlayer
import com.example.ck_android.ui.screens.parttwo.QuestionGrid

@Composable
fun PartThreeItemScreen(
    navController: NavController,
    partThreeItemViewModel: PartThreeItemViewModel,
    mainViewModel: MainViewModel,
    id: String
) {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlueBackground)
    )
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val paddingVertical = (screenHeight * 0.1f).dp

    val questionData = partThreeItemViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        partThreeItemViewModel.getPartThreeQuestion(id)
    }
    val questionList = questionData.value.data

    val baseUrl = ApiConfig.BASE_BACKEND
    val audioUrl = baseUrl + navController.previousBackStackEntry?.arguments?.getString("audioUrl")

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var answers by remember { mutableStateOf(mutableMapOf<Int, String>()) }

    var expanded by remember { mutableStateOf(false) }

    // Hiển thị câu hỏi và lựa chọn đáp án A,B,C,D
    if (questionList.isNotEmpty()) {
        val currentQuestion = questionList[currentQuestionIndex]
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingVertical,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = paddingVertical
                )
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Hiển thị audio
            AudioPlayer(audioUrl = audioUrl)
            // Bảng câu hỏi
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.padding(top = 10.dp)
            ) {
                // Chọn icon tương ứng với trạng thái mở hoặc đóng bảng câu hỏi
                Icon(
                    imageVector = if (expanded) Icons.Filled.Close else Icons.Filled.Menu,
                    contentDescription = if (expanded) "Đóng bảng câu hỏi" else "Mở bảng câu hỏi"
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Hiển thị số câu hỏi
                Text(
                    text = "Câu hỏi ${currentQuestion.questionNumber}",
                    style = MaterialTheme.typography.headlineSmall
                )

                // Button nộp bài
                Button(
                    onClick = {
                        val correctAnswers = questionList.map { it.correctAnswer }
                        val userAnswers = answers
                        var correctCount = 0
                        for (i in correctAnswers.indices) {
                            if (correctAnswers[i] == userAnswers[i]) {
                                correctCount++;
                            }
                        }
                        val correctAnswersCount = answers.size
                        val incorrectAnswersCount = 10 - correctCount
                        val score = (correctCount * 5).toInt()
                        navController.currentBackStackEntry?.arguments?.putString(
                            "score",
                            score.toString()
                        )
                        navController.currentBackStackEntry?.arguments?.putString(
                            "correctAnswers",
                            correctAnswers.toString()
                        )
                        navController.currentBackStackEntry?.arguments?.putString(
                            "userAnswers",
                            userAnswers.toString()
                        )
                        val incorrectAnswers =
                            userAnswers.filter { it.value != correctAnswers[it.key] }
//                        partOneItemViewModel.postScoreToeic(
//                            score,
//                            id,
//                            correctAnswers = correctCount.toString(),
//                            incorrectAnswers = incorrectAnswersCount.toString()
//                        )
                        navController.navigate(Screen.PartOneResult.route.replace("{id}", id))
                    },
                    modifier = Modifier
                        .height(48.dp)
                        .padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    shape = MaterialTheme.shapes.medium,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Nộp bài",
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(text = "Nộp bài", style = MaterialTheme.typography.labelLarge)
                }
            }

            // Hiển thị hình ảnh
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(currentQuestion.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Question Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .aspectRatio(1f)
                    .padding(vertical = 8.dp)
            )

//            Spacer(modifier = Modifier.height(16.dp))

            // Các lựa chọn A, B, C, D
            val options = listOf("A", "B", "C", "D")
            val isExampleQuestion = currentQuestion.questionNumber == "0"
            Column {
                options.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                enabled = !isExampleQuestion,
                                selected = (answers[currentQuestionIndex] == option),
                                onClick = {
                                    answers = answers.toMutableMap().apply {
                                        put(currentQuestionIndex, option)
                                    }
                                }
                            )
                            .padding(4.dp)
                    ) {
                        RadioButton(
                            enabled = !isExampleQuestion,
                            selected = (answers[currentQuestionIndex] == option),
                            onClick = {
                                answers = answers.toMutableMap().apply {
                                    put(currentQuestionIndex, option)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = option)
                    }
                }
            }

//            Spacer(modifier = Modifier.height(32.dp))

            // Các nút "Câu trước" - "Câu tiếp theo"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        if (currentQuestionIndex > 0) {
                            currentQuestionIndex--
                        }
                    },
                    enabled = currentQuestionIndex > 0,
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Text(text = "Câu trước")
                }

                Button(
                    onClick = {
                        if (currentQuestionIndex < questionList.lastIndex) {
                            currentQuestionIndex++
                        }
                    },
                    enabled = currentQuestionIndex < questionList.lastIndex,
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Text(text = "Câu tiếp theo")
                }
            }
        }
        // Nếu expanded thì vẽ bảng chọn câu hỏi ở giữa
        if (expanded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(400.dp)
                        .padding(16.dp)
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    // Bảng số câu hỏi
                    QuestionGrid(
                        questionCount = questionList.size,
                        currentQuestionIndex = currentQuestionIndex,
                        answers = answers,
                        onQuestionSelected = { selectedIndex ->
                            currentQuestionIndex = selectedIndex
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}