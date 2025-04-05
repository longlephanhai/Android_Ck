package com.example.ck_android.repositories

import com.example.ck_android.model.ChatRequest
import com.example.ck_android.model.ChatResponse
import com.example.ck_android.model.CheckCodeRequest
import com.example.ck_android.model.CheckCodeResponse
import com.example.ck_android.model.GrammarItemResponse
import com.example.ck_android.model.GrammarResponse
import com.example.ck_android.model.LoginRequest
import com.example.ck_android.model.LoginRespon
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

    override suspend fun getGrammars(accessToken: String): GrammarResponse {
        return apiClient.apiService.getGrammars(accessToken)
    }

    override suspend fun getGrammarItem(
        access_token: String,
        slug: String
    ): GrammarItemResponse {
        return apiClient.apiService.getGrammarItem(access_token, slug)
    }

    override suspend fun chatGpt(
        access_token: String,
        chatRequest: ChatRequest
    ): ChatResponse {
        return apiClient.apiService.chatGpt(access_token, chatRequest)
    }


}