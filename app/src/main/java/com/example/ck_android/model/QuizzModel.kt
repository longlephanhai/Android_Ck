package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class TestQuizzResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<TestQuizzData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class TestQuizzData(
    val _id: String,
    val title: String,
    val vocb: String,
    val meaning: String,
)

data class TestQuizzRequest(
    val slug: String,
    val title: String,
)