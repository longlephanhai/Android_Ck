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
            .padding(24.dp)
            .clip(RoundedCornerShape(32.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onPrevious,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "Previous",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
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
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isFavorite) R.drawable.star else R.drawable.star2
                        ),
                        contentDescription = "Favorite",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }

            }

            // Word with image
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Image (if available)
                AsyncImage(
                    model = item.img,
                    contentDescription = "Vocabulary image",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.vocb,
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {


                        Text(
                            text = item.pronounce,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )

                        IconButton(
                            onClick = { onSpeak(item.vocb) },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_speaker),
                                contentDescription = "Pronounce",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }

            // Meaning section
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Meaning",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = item.meaning,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            // Example section
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Example",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                        .padding(20.dp)
                ) {
                    Text(
                        text = "\"${item.example}\"",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }

            // Next button at bottom
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(
                    onClick = onNext,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = "Next",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
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



