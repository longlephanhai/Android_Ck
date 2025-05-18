package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class PartFiveQuestionResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<PartFiveQuestionData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class PartFiveQuestionData(
    val _id: String,
    val questionNumber: String,
    val imageUrl: String,
    val correctAnswer: String
)