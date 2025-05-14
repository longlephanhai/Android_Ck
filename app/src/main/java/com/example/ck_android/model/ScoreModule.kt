package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class ScoreResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: ScoreData = ScoreData(
        score = 0,
        userId = "",
        examId = "",
        correctAnswers = "",
        incorrectAnswers = ""
    ),
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

data class ScoreListResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<ScoreDataList> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class ScoreDataList(
    val score: Int,
    val userId: String,
    val examId: ExamDataId = ExamDataId(
        _id = "",
        name = "",
        description = ""
    ),
    val correctAnswers: String,
    val incorrectAnswers: String
)

data class ExamDataId(
    val _id: String,
    val name: String,
    val description: String,
)