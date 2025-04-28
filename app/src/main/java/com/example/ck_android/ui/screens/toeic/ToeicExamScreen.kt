package com.example.ck_android.ui.screens.toeic

import android.media.MediaPlayer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ck_android.MainViewModel

@Composable
fun ToeicExamScreen(
    navController: NavController,
    toeicExamViewModel: ToeicExamViewModel,
    mainViewModel: MainViewModel,
    id: String
) {
    val questionData = toeicExamViewModel.question.collectAsState()
    val toeicExamData = toeicExamViewModel.toeicExam.collectAsState()

    LaunchedEffect(Unit) {
        toeicExamViewModel.getQuestionToeic(toeicExamViewModel.getToken() ?: "", id)
    }
    val questionList = questionData.value.data

    val baseUrl = "http://10.0.2.2:8080/"
//    val baseUrl="https://education-be-tuv3.onrender.com/"
    val audioUrl = baseUrl + navController.previousBackStackEntry?.arguments?.getString("audioUrl")
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    var isAudioPlaying by remember { mutableStateOf(false) }

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var answers by remember { mutableStateOf(mutableMapOf<Int, String>()) }

    var expanded by remember { mutableStateOf(false) }


    // Hiển thị audio
    fun playAudio() {
        if (!isAudioPlaying) {
            mediaPlayer?.release() // Giải phóng bộ nhớ của MediaPlayer trước đó nếu có
            mediaPlayer = MediaPlayer().apply {
                setDataSource(audioUrl)
                setOnCompletionListener {
                    release()
                    mediaPlayer = null
                    isAudioPlaying = false
                }
                prepareAsync() // Chuẩn bị âm thanh không đồng bộ
                setOnPreparedListener {
                    start() // Bắt đầu phát âm thanh
                    isAudioPlaying = true
                }
            }
        }
    }


    // Hiển thị câu hỏi và lựa chọn đáp án A,B,C,D
    if (questionList.isNotEmpty()) {
        val currentQuestion = questionList[currentQuestionIndex]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Hiển thị số câu hỏi
            Text(
                text = "Câu hỏi ${currentQuestion.questionNumber}",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

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
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                            .padding(8.dp)
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

            Spacer(modifier = Modifier.height(32.dp))

            // Các nút "Câu trước" - "Câu tiếp theo"
            Row(
                modifier = Modifier.fillMaxWidth(),
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
            // Bảng câu hỏi
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                Text(text = if (expanded) "Đóng bảng câu hỏi" else "Mở bảng câu hỏi")
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
                    // Nút phát âm thanh
                    Button(
                        onClick = { playAudio() }, // Gọi hàm playAudio khi nhấn nút
                        modifier = Modifier.padding(bottom = 40.dp)
                    ) {
                        Text(text = "Phát âm thanh")
                    }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionGrid(
    questionCount: Int,
    currentQuestionIndex: Int,
    answers: Map<Int, String>,
    onQuestionSelected: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(6), // 6 cột
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp)
    ) {
        items(count = questionCount) { index ->
            val isSelected = index + 1 == currentQuestionIndex
            val isAnswered = answers.containsKey(index + 1)
            val backgroundColor = when {
                isSelected -> Color(0xFFD0E8FF) // câu đang chọn
                isAnswered -> Color(0xFFC8E6C9) // câu đã làm (ví dụ xanh lá nhạt)
                else -> Color(0xFFF5F5F5) // chưa chọn
            }
            val borderColor = when {
                isSelected -> Color.Blue
                isAnswered -> Color.Green
                else -> Color.Gray
            }
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .width(48.dp)
                    .height(48.dp)
                    .background(backgroundColor)
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = borderColor
                    )
                    .clickable {
                        onQuestionSelected(index + 1)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "${index + 1}")
            }
        }
    }
}