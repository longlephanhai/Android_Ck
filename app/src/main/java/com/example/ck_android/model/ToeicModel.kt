package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class ToeicResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<ToeicData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class ToeicData(
    val _id: String,
    val name: String,
    val description: String,
    val audioUrl: String
)

data class ToeicExamResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: ToeicExamData = ToeicExamData(
        _id = "",
        name = "",
        description = "",
        audioUrl = ""
    ),
    val status: LoadStatus = LoadStatus.Init()
)

data class ToeicExamData(
    val _id: String,
    val name: String,
    val description: String,
    val audioUrl: String
)

data class QuestionResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<QuestionData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class QuestionData(
    val _id: String,
    val examId: String,
    val part: String,
    val questionNumber: String,
    val imageUrl: String,
    val correctAnswer: String,
)



