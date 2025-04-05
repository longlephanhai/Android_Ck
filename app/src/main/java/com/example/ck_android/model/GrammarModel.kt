package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class GrammarResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<GrammarData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class GrammarData(
    val _id: String,
    val title: String,
    val content: String,
    val slug: String
)