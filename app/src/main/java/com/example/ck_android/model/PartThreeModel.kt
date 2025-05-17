package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class PartThreeResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<PartThreeData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class PartThreeData(
    val _id: String,
    val name: String,
    val description: String,
    val audioUrl: String
)