package com.example.ck_android.ui.screens.register

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.ck_android.MainViewModel
import com.example.ck_android.Screen
import com.example.ck_android.common.enum.LoadStatus

@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel,
    mainViewModel: MainViewModel
) {
    val state = registerViewModel.uiState.collectAsState()
    val blueColor = Color(0xFF1877F2)
    val imagePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                registerViewModel.updateAvatar(it)
            }
        }

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
                    navController.navigate("${Screen.CheckCode.route}?ID=${state.value._id}") {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                }
            }
        } else {
            if (state.value.status is LoadStatus.Error) {
                mainViewModel.setError(state.value.status.description)
                registerViewModel.reset()
            }
            Column(modifier = Modifier.padding(16.dp)) {
                state.value.avatar?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(color = blueColor)
                    )
                }
                Button(
                    onClick = { imagePicker.launch("image/*") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = blueColor,
                        contentColor = Color.White
                    )
                ) {
                    Text("Chọn ảnh")
                }
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = state.value.name,
                    onValueChange = { registerViewModel.updateName(it) },
                    label = { Text("Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
                    placeholder = { Text("Enter your name") },
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
                    value = state.value.email,
                    onValueChange = { registerViewModel.updateEmail(it) },
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
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = state.value.password,
                    onValueChange = { registerViewModel.updatePassword(it) },
                    label = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
                    placeholder = { Text("Enter your password") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = blueColor,
                        unfocusedIndicatorColor = Color.Gray,
                        focusedTextColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = state.value.phone,
                    onValueChange = { registerViewModel.updatePhone(it) },
                    label = { Text("Phone") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
                    placeholder = { Text("Enter your phone") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = blueColor,
                        unfocusedIndicatorColor = Color.Gray,
                        focusedTextColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                Spacer(modifier = Modifier.height(20.dp))
                ElevatedButton(
                    onClick = {
                        registerViewModel.register()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(6.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = blueColor,
                        contentColor = Color.White
                    )
                ) {
                    Text("Register", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}