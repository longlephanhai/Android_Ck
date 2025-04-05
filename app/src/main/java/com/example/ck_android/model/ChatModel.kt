package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class ChatRequest(
    val text: String = "",
    val status: LoadStatus = LoadStatus.Init()
)

data class ChatResponse(
    val text: String,
    val audio: String,
)