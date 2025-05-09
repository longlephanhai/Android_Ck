package com.example.ck_android.ui.screens.vocabulary

import android.speech.tts.TextToSpeech
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ck_android.MainViewModel
import com.example.ck_android.model.VocabularyCategoryItemData
import com.example.ck_android.R
import com.example.ck_android.model.FavouriteListData
import java.util.Locale


@Composable
fun VocabularyCategory(
    navController: NavController,
    vocabularyCategoryViewModel: VocabularyCategoryViewModel,
    mainViewModel: MainViewModel,
    slug: String,
    category: String
) {
    val state = vocabularyCategoryViewModel.uiState.collectAsState()
    val currentIndex = remember {
        mutableStateOf(0)
    }

    val favouriteList = vocabularyCategoryViewModel.favouriteList.collectAsState()

    LaunchedEffect(Unit) {
        vocabularyCategoryViewModel.getVocabularyCategory(
            vocabularyCategoryViewModel.getToken() ?: "",
            slug,
            category
        )
    }

    LaunchedEffect(favouriteList.value) {
        vocabularyCategoryViewModel.getFavouriteVocbList()
    }


    val favouriteListValue = favouriteList.value.data


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

    val vocabularyList = state.value.data

    if (vocabularyList.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            // Full-screen vocabulary card
            VocabularyCardFull(
                item = vocabularyList[currentIndex.value],
                onSpeak = { text ->
                    textToSpeech?.speak(
                        text,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                    )
                },
                onNext = {
                    if (currentIndex.value < vocabularyList.size - 1) {
                        currentIndex.value++
                    }
                },
                onPrevious = {
                    if (currentIndex.value > 0) {
                        currentIndex.value--
                    }
                },
                modifier = Modifier.fillMaxSize(),
                vocabularyCategoryViewModel = vocabularyCategoryViewModel,
                favouriteListValue = favouriteListValue
            )

            // Page indicator
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            ) {
                PageIndicator(
                    currentPage = currentIndex.value,
                    totalPages = vocabularyList.size
                )
            }
        }
    } else {
        // Loading or empty state
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}


@OptIn(UnstableApi::class)
@Composable
fun VocabularyCardFull(
    item: VocabularyCategoryItemData,
    onSpeak: (String) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier,
    vocabularyCategoryViewModel: VocabularyCategoryViewModel,
    favouriteListValue: List<FavouriteListData>
) {
    val isFavorite = favouriteListValue.any { it.vocbId._id == item._id }


    Card(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(32.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onPrevious,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0x22000000))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "Previous",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }



                IconButton(
                    onClick = {
                        if (isFavorite) {
                            vocabularyCategoryViewModel.cancelFavouriteVocb(item._id)
                        } else {
                            vocabularyCategoryViewModel.postFavourite(item._id)
                        }
                        vocabularyCategoryViewModel.getFavouriteVocbList()
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFF8E1))
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isFavorite) R.drawable.star else R.drawable.star2
                        ),
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color(0xFFFFD700) else Color(0xFF90A4AE),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            // Word + Image
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = item.img,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = item.vocb,
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = Color(0xFF0D47A1)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.pronounce,
                        style = MaterialTheme.typography.headlineSmall.copy(fontStyle = FontStyle.Italic),
                        color = Color.Gray
                    )
                    IconButton(onClick = { onSpeak(item.vocb) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_speaker),
                            contentDescription = "Pronounce",
                            tint = Color(0xFF0D47A1),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            // Meaning
            Column {
                Text("Meaning", fontWeight = FontWeight.SemiBold, color = Color.Gray)
                Text(item.meaning, style = MaterialTheme.typography.bodyLarge)
            }

            // Example
            Column {
                Text("Example", fontWeight = FontWeight.SemiBold, color = Color.Gray)
                Box(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF1F8E9))
                        .padding(16.dp)
                ) {
                    Text("\"${item.example}\"", fontStyle = FontStyle.Italic)
                }
            }

            // Next
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                IconButton(
                    onClick = onNext,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0x22000000))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = "Next",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }

}

@Composable
fun PageIndicator(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == currentPage) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outlineVariant
                    )
            )
        }
    }
}



