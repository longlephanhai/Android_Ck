package com.example.ck_android.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.material3.OutlinedTextFieldDefaults

@Composable
fun ProfileScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val uiState = homeViewModel.profileState.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.getProfile()
    }
    val profile = uiState.value.data
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(profile?.name ?: "") }
    var email by remember { mutableStateOf(profile?.email ?: "") }
    var phone by remember { mutableStateOf(profile?.phone ?: "") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
            .padding(24.dp)
    ) {
        if (profile != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.TopCenter),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar
                    if (profile.avatar != null) {
                        AsyncImage(
                            model = profile.avatar,
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .border(3.dp, Color(0xFF2196F3), CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Default Avatar",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF90CAF9)),
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Name
                    if (isEditing) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Name") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF2196F3),
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color(0xFF2196F3),
                                unfocusedLabelColor = Color.Gray
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                        )
                    } else {
                        Text(
                            text = profile.name ?: "Không có tên",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFF0D47A1)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

// Email
                    if (isEditing) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors =OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF2196F3),
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color(0xFF2196F3),
                                unfocusedLabelColor = Color.Gray
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                        )
                    } else {
                        ProfileItemRow(
                            icon = Icons.Default.Email,
                            label = profile.email ?: "Không có email"
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

// Phone
                    if (isEditing) {
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Phone") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF2196F3),
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color(0xFF2196F3),
                                unfocusedLabelColor = Color.Gray
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                        )
                    } else {
                        ProfileItemRow(
                            icon = Icons.Default.Phone,
                            label = profile.phone ?: "Không có số điện thoại"
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (isEditing) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Nút Cập nhật thông tin
                                Button(
                                    onClick = {
                                        // Xử lý logic cập nhật (bạn sẽ thực hiện phần này sau)
                                        isEditing = false
//            homeViewModel.updateProfile(name, email, phone)
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFF2196F3
                                        )
                                    ), // Màu xanh dương
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = "Cập nhật",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White
                                    )
                                }

                                // Nút Hủy
                                Button(
                                    onClick = { isEditing = false },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFFB0BEC5
                                        )
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = "Hủy",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White
                                    )
                                }
                            }

                        } else {
                            Button(
                                onClick = { isEditing = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .padding(horizontal = 16.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF2196F3
                                    )
                                ),
                                contentPadding = ButtonDefaults.ContentPadding
                            ) {
                                Text(
                                    text = "Chỉnh sửa thông tin",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileItemRow(icon: ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF1976D2),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}
