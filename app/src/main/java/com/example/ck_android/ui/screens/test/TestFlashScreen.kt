package com.example.ck_android.ui.screens.test

import android.speech.tts.TextToSpeech
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.ck_android.MainViewModel
import com.example.ck_android.R
import com.example.ck_android.model.VocabularyCategoryItemData
import java.util.Locale

@Composable
fun TestFlashScreen(
    navController: NavController,
    testFlashViewModel: TestFlashViewModel,
    mainViewModel: MainViewModel,
    slug: String,
    category: String
) {
    val uiState by testFlashViewModel.uiState.collectAsState()
    val vocabularyList = uiState.data
    val currentIndex = remember { mutableIntStateOf(0) }

    val currentItem = vocabularyList.getOrNull(currentIndex.intValue)

    val context = LocalContext.current
    var textToSpeech by remember {
        mutableStateOf<TextToSpeech?>(null)
    }

    // Khởi tạo TextToSpeech khi Composable được gọi
    LaunchedEffect(Unit) {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.US
            }
        }
    }

    // Hủy TextToSpeech khi Composable bị dispose
    DisposableEffect(Unit) {
        onDispose {
            textToSpeech?.stop()
            textToSpeech?.shutdown()
        }
    }

    LaunchedEffect(Unit) {
        testFlashViewModel.getVocabularyCategory(
            testFlashViewModel.getToken() ?: "",
            slug,
            category
        )
    }

    currentItem?.let {
        FlashCardItem(
            vocab = it,
            onNext = {
                if (currentIndex.intValue < vocabularyList.size - 1)
                    currentIndex.intValue++
            },
            onSpeak = { text ->
                textToSpeech?.speak(
                    text,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    null
                )
            },
        )
    }
}

@Composable
fun FlashCardItem(
    vocab: VocabularyCategoryItemData,
    onNext: () -> Unit,
    onSpeak: (String) -> Unit,
) {
    var isFlipped by remember { mutableStateOf(false) }

    val rotation = animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "Card Flip Animation"
    ).value


    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { isFlipped = !isFlipped },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(400.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12 * density

                }
        ) {
            if (
                rotation <= 90f || rotation >= 270f
            ) {
                // Mặt trước
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = vocab.img,
                            contentDescription = "Vocabulary image",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Text(text = vocab.meaning, style = MaterialTheme.typography.titleLarge)
                        Button(onClick = {
                            onNext()
                            isFlipped = false
                        }) {
                            Text("Từ kế tiếp")
                        }
                    }
                }
            } else {
                // Mặt sau
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            rotationY = 180f
                            cameraDistance = 12 * density

                        },
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = vocab.vocb, style = MaterialTheme.typography.headlineMedium)
                        IconButton(
                            onClick = { onSpeak(vocab.vocb) },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_speaker),
                                contentDescription = "Pronounce",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Text(
                            text = vocab.pronounce,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                        Text(text = vocab.example, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

