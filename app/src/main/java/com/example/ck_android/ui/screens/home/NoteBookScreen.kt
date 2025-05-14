package com.example.ck_android.ui.screens.home

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ck_android.R
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.FavouriteListData
import com.example.ck_android.model.VocbIdData
import com.example.ck_android.ui.screens.vocabulary.VocabularyCategoryViewModel
import java.util.Locale

@Composable
fun NoteBookScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
) {
    val uiState = homeViewModel.favouriteList.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.getFavouriteVocbList()
    }

    val status = uiState.value.status
    val favouriteList = uiState.value.data

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

    when (status) {
        is LoadStatus.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is LoadStatus.Success -> {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(favouriteList.size) { index ->
                    VocabularyCard(
                        item = favouriteList[index].vocbId,
                        onSpeak = { text ->
                            textToSpeech?.speak(
                                text,
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                null
                            )
                        },
                    ) {
                        homeViewModel.cancelFavouriteVocb(favouriteList[index].vocbId._id)
                    }
                }
            }

        }

        else -> {}
    }
}

@Composable
fun VocabularyCard(
    item: VocbIdData,
    onSpeak: (String) -> Unit,
    onRemoveFavourite: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        onSpeak(item.vocb)
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_speaker),
                        contentDescription = "Pronounce",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${item.vocb} ${item.pronounce}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${convertType(item.type)} - ${item.meaning}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }


                IconButton(
                    onClick = {
                        onRemoveFavourite()
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.star),
                        contentDescription = "Favorite",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            if (item.example.isNotBlank()) {
                Text(
                    text = "📘 Ví dụ: ${item.example}",
                    modifier = Modifier.padding(top = 8.dp),
                    fontStyle = FontStyle.Italic
                )
            }

            if (item.img.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = item.img,
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }
    }
}

fun convertType(type: String): String {
    return when (type) {
        "noun" -> "(n)"
        "verb" -> "(v)"
        "adjective" -> "(adj)"
        "adverb" -> "(adv)"
        "preposition" -> "(prep)"
        "conjunction" -> "(conj)"
        "interjection" -> "(interj)"
        "pronoun" -> "(pron)"
        "numeral" -> "(num)"
        else -> ""
    }
}
