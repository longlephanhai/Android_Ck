package com.example.ck_android.repositories

import com.example.ck_android.model.LoginRequest
import com.example.ck_android.model.LoginRespon
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    // api auth
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginRespon
}