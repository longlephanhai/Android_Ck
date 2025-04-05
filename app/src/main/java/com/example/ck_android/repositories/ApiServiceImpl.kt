package com.example.ck_android.repositories

import com.example.ck_android.model.LoginRequest
import com.example.ck_android.model.LoginRespon
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val apiClient: ApiClient
) : ApiService {
    override suspend fun login(loginRequest: LoginRequest): LoginRespon {
        return apiClient.apiService.login(loginRequest)
    }
}