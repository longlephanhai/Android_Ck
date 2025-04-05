package com.example.ck_android.ui.screens.start

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel
import com.example.ck_android.R
import com.example.ck_android.Screen

@Composable
fun StartScreen(
    navController: NavController,
    startViewModel: StartViewModel,
    mainViewModel: MainViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Welcome to Edu App",
                fontSize = 30.sp,
                color = Color(0xFF1877F2),
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Nền tảng học tiếng anh trực tuyến tuyến hàng đầu Việt Nam",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))


            SocialLoginButton(
                text = "Đăng nhập với Facebook",
                icon = R.drawable.facebook,
                backgroundColor = Color(0xFF1877F2),
                onClick = { /* Xử lý đăng nhập Facebook */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nút đăng nhập Google
            SocialLoginButton(
                text = "Đăng nhập với Google",
                icon = R.drawable.google,
                backgroundColor = Color.White,
                textColor = Color.Black,
                borderColor = Color.LightGray,
                onClick = { /* Xử lý đăng nhập Google */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { navController.navigate(Screen.Login.route)},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50.dp),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Text(text = "Đăng nhập với email", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))


            Row {
                Text(text = "Chưa có tài khoản?", color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Đăng ký ngay",
                    color = Color(0xFF1877F2),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { /* Chuyển sang màn hình đăng ký */ }
                )
            }
        }
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    icon: Int,
    backgroundColor: Color,
    textColor: Color = Color.White,
    borderColor: Color? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .then(if (borderColor != null) Modifier.border(1.dp, borderColor, RoundedCornerShape(50.dp)) else Modifier),
        shape = RoundedCornerShape(50.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = textColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}