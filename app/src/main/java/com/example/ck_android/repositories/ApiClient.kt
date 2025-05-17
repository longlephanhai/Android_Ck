package com.example.ck_android.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ApiClient @Inject constructor() {
    object ApiConfig {
        const val BASE_URL = "http://10.0.2.2:8080/api/v1/"
        const val BASE_BACKEND = "http://10.0.2.2:8080/"
//        const val BASE_URL = "https://education-be-tuv3.onrender.com/api/v1/"
//        const val BASE_BACKEND="https://education-be-tuv3.onrender.com/"
    }

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}