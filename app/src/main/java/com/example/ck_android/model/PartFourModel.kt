package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class PartFourResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<PartFourData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class PartFourData(
    val _id: String,
    val name: String,
    val description: String,
    val audioUrl: String
)