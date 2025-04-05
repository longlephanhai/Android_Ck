package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class LoginRespon(
    val statusCode: Int,
    val message: String,
    val data: LoginData,
)

data class LoginRequest(
    val email: String = "",
    val password: String = "",
    val status: LoadStatus = LoadStatus.Init()
)

data class LoginData(
    val user: User,
    val access_token: String
)

data class User(
    val name: String,
    val email: String,
    val _id: String,
)