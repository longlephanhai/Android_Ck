package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class ScoreResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<ScoreData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class ScoreData(
    val score: Int,
    val userId: String,
    val examId: String,
    val correctAnswers: String,
    val incorrectAnswers: String
)

data class ScoreRequest(
    val score: Int,
    val userId: String,
    val examId: String,
    val correctAnswers: String,
    val incorrectAnswers: String
)