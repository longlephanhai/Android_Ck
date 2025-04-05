package com.example.ck_android.ui.screens.login

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel
import com.example.ck_android.R
import com.example.ck_android.Screen
import com.example.ck_android.common.enum.LoadStatus

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    mainViewModel: MainViewModel
) {
    val state = loginViewModel.uiState.collectAsState()
    val blueColor = Color(0xFF1877F2)
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (state.value.status is LoadStatus.Loading) {
            CircularProgressIndicator()
        } else if (state.value.status is LoadStatus.Success) {
            LaunchedEffect(state.value.status) {
                if (state.value.status is LoadStatus.Success) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            }

        } else {
            if (state.value.status is LoadStatus.Error) {
                mainViewModel.setError(state.value.status.description)
                loginViewModel.reset()
            }
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.background_login),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome to Edu App",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = blueColor
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        value = state.value.email,
                        onValueChange = {
                            loginViewModel.updateEmail(it)
                        },
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(12.dp)),
                        placeholder = { Text("Enter your email") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = blueColor,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedTextColor = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = state.value.password,
                        onValueChange = {
                            loginViewModel.updatePassword(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(12.dp)),
                        label = { Text("Password") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = blueColor,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedTextColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Forgot Password?",
                            color = blueColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                println("Forgot Clicked!")
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    ElevatedButton(
                        onClick = { loginViewModel.login() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(6.dp, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = blueColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Login", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Don't have an account?", color = Color.Black)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Register",
                            color = blueColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.Register.route)
                            }
                        )
                    }
                }
            }


        }
    }

}