package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class TestDefineResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<TestDefineData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class TestDefineRequest(
    val slug: String,
    val title: String,
)

data class TestDefineData(
    val question: String,
    val answers: List<AnswerItem>
)

data class AnswerItem(
    val text: String,
    val isCorrect: Boolean
)