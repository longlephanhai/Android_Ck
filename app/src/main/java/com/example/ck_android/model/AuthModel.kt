package com.example.ck_android.model

import android.net.Uri
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

data class RegisterRequest(
    var avatar: Uri? = null,
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val phone: String = "",
    val _id: String = "",
    val status: LoadStatus = LoadStatus.Init()
)

data class RegisterResponse(
    val statusCode: Int,
    val message: String,
    val data: RegisterData,
)

data class RegisterData(
    val _id: String,
)

data class CheckCodeRequest(
    val _id: String = "",
    val code: String = "",
    val status: LoadStatus = LoadStatus.Init()
)

data class CheckCodeResponse(
    val statusCode: Int,
    val message: String,
    val data: CheckCodeData,
)

data class CheckCodeData(
    val isBeforeCheck: Boolean
)

data class User(
    val name: String,
    val email: String,
    val id: String,
)

data class UserProfileResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: UserProfileData = UserProfileData(
        _id = "",
        email = "",
        name = "",
        phone = "",
        avatar = ""
    ),
    val status: LoadStatus = LoadStatus.Init()
)

data class UserProfileData(
    val _id: String,
    val email: String,
    val name: String,
    val phone: String,
    val avatar: String
)

data class UpdateUserProfileResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: dataUSer = dataUSer(
        _id = ""
    ),
    val status: LoadStatus = LoadStatus.Init()
)

data class dataUSer(
    val _id: String,
)

data class UpdateUserProfileRequest(
    var avatar: Uri? = null,
    val email: String = "",
    val name: String = "",
    val phone: String = "",
)

