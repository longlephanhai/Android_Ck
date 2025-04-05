package com.example.ck_android.repositories

import com.example.ck_android.model.CheckCodeRequest
import com.example.ck_android.model.CheckCodeResponse
import com.example.ck_android.model.LoginRequest
import com.example.ck_android.model.LoginRespon
import com.example.ck_android.model.RegisterRequest
import com.example.ck_android.model.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val apiClient: ApiClient
) : ApiService {
    override suspend fun login(loginRequest: LoginRequest): LoginRespon {
        return apiClient.apiService.login(loginRequest)
    }

    override suspend fun register(
        avatar: MultipartBody.Part?,
        name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        phone: RequestBody
    ): RegisterResponse {
        return apiClient.apiService.register(avatar, name, email, password, phone)
    }

    override suspend fun checkCode(checkCodeRequest: CheckCodeRequest): CheckCodeResponse {
        return apiClient.apiService.checkCode(checkCodeRequest)
    }


}