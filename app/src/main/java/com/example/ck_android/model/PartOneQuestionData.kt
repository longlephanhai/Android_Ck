package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class PartOneQuestionResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<PartOneQuestionData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class PartOneQuestionData(
    val _id: String,
    val questionNumber: String,
    val imageUrl: String,
    val correctAnswer: String
)
