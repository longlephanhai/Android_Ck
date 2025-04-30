package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class GeminiResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: String = "",
    val status: LoadStatus = LoadStatus.Init()
)

data class GeminiRequest(
    val prompt: String
)

data class TitleGeminiResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: TitleGeminiData = TitleGeminiData(
        title = "",
        hints = ""
    ),
    val status: LoadStatus = LoadStatus.Init()
)

data class TitleGeminiData(
    val title: String,
    val hints: String,
)