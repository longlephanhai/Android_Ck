package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class PartFiveResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<PartFiveData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class PartFiveData(
    val _id: String,
    val name: String,
    val description: String,
)