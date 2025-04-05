package com.example.ck_android.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel
import com.example.ck_android.R
import com.example.ck_android.Screen
import com.example.ck_android.ui.screens.content.ContentScreen
import com.example.ck_android.ui.screens.content.ContentViewModel

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Content : BottomNavItem("content", "Content", Icons.Default.AccountBox)
    object Profile : BottomNavItem("profile", "Profile", Icons.Default.Person)
    object Settings : BottomNavItem("settings", "Settings", Icons.Default.Settings)

    companion object {
        val items = listOf(Home,Content, Profile, Settings)
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
                            // TODO: thực hiện điều hướng nếu bạn muốn
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
            }
        }
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
