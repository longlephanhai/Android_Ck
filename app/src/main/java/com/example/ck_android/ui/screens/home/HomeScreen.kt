package com.example.ck_android.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ck_android.MainViewModel
import com.example.ck_android.R
import com.example.ck_android.Screen
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.VocbIdData
import com.example.ck_android.ui.screens.vocabulary.VocabularyCategoryViewModel
import com.example.ck_android.ui.screens.vocabulary.VocabularyViewModel


sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Trang chủ", Icons.Default.Home)
    object Content : BottomNavItem("book", "Sổ tay", Icons.Default.AccountBox)
    object Profile : BottomNavItem("profile", "Thông tin", Icons.Default.Person)
    object Settings : BottomNavItem("history", "Lịch sử làm bài", Icons.Default.Done)

    companion object {
        val items = listOf(Home, Content, Profile, Settings)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
) {
    val blueColor = Color(0xFF1877F2)
    val currentRoute = remember { mutableStateOf("home") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Trang Chủ", color = Color.White)
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(82.dp),
                        tint = Color.Unspecified
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = blueColor)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                BottomNavItem.items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute.value == item.route,
                        onClick = {
                            currentRoute.value = item.route
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = blueColor,
                            selectedTextColor = blueColor,
                            indicatorColor = Color(0x331877F2)
                        )
                    )
                }
            }
        },
        containerColor = Color(0xFFF1F5F9)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color.White)
        ) {
            if (currentRoute.value == "home") {
                MainScreen(navController)
            } else if (currentRoute.value == "book") {
                NoteBookScreen(
                    navController = navController,
                    homeViewModel = homeViewModel
                )
            } else if (currentRoute.value == "profile") {
                ProfileScreen(navController)
            } else if (currentRoute.value == "history") {
                HistoryScreen(navController)
            }
        }
    }
}

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

    when (status) {
        is LoadStatus.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is LoadStatus.Success -> {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(favouriteList.size) { index ->
                    VocabularyCard(item = favouriteList[index].vocbId) {
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
    onRemoveFavourite: () -> Unit
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
//                        if (isFavorite) {
//                            vocabularyCategoryViewModel.cancelFavouriteVocb(item._id)
//
//                        } else {
//                            vocabularyCategoryViewModel.postFavourite(item._id)
//                        }
//                        vocabularyCategoryViewModel.getFavouriteVocbList()
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.star ),
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




@Composable
fun MainScreen(navController: NavController) {
    val blueColor = Color(0xFF1877F2)
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Nâng cao kỹ năng tiếng Anh của bạn với ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Text(
                text = "Edu App",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = blueColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Cùng khám phá các khóa học tiếng Anh trực tuyến từ cơ bản đến nâng cao với đội ngũ giảng viên chất lượng và phương pháp học tập hiệu quả.",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row {
                Button(
                    onClick = { navController.navigate(Screen.Content.route) },
                    colors = ButtonDefaults.buttonColors(containerColor = blueColor),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Bắt đầu học", color = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedButton(
                    onClick = { /* TODO: Điều hướng đến giới thiệu */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = blueColor)
                ) {
                    Text("Tìm hiểu thêm")
                }
            }
            Image(
                painter = painterResource(id = R.drawable.study_illustration),
                contentDescription = "Study Illustration",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
fun ProfileScreen(navController: NavController) {
    Text(text = "ProfileScreen")
}

@Composable
fun HistoryScreen(navController: NavController) {
    Text(text = "HistoryScreen")
}