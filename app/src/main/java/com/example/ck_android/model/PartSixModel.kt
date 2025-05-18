package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class PartSixResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<PartSixData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class PartSixData(
    val _id: String,
    val name: String,
    val description: String,
)