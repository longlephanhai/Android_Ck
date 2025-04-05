package com.example.ck_android.repositories

import com.example.ck_android.model.CheckCodeRequest
import com.example.ck_android.model.CheckCodeResponse
import com.example.ck_android.model.GrammarResponse
import com.example.ck_android.model.LoginRequest
import com.example.ck_android.model.LoginRespon
import com.example.ck_android.model.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    // api auth
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginRespon

    @Multipart
    @POST("auth/register")
    suspend fun register(
        @Part avatar: MultipartBody.Part?, // Avatar là phần upload file
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phone") phone: RequestBody
    ): RegisterResponse

    @POST("auth/check-code")
    suspend fun checkCode(@Body checkCodeRequest: CheckCodeRequest): CheckCodeResponse

    // api grammar
    @GET("grammars")
    suspend fun getGrammars(@Header("Authorization") access_token: String): GrammarResponse
}