package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class PartTwoQuestionResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<PartTwoQuestionData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class PartTwoQuestionData(
    val _id: String,
    val questionNumber: String,
    val imageUrl: String,
    val correctAnswer: String
)
