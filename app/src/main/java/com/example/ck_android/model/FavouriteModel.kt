package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class FavouriteResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: FavouriteData = FavouriteData(
        userId = "",
        vocbId = "",
        isFavourite = false,
        isDeleted = false
    ),
    val status: LoadStatus = LoadStatus.Init()
)

data class FavouriteData(
    val userId: String,
    val vocbId: String,
    val isFavourite: Boolean,
    val isDeleted: Boolean,
)

data class FavouriteRequest(
    val vocbId: String
)

data class FavouriteListResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<FavouriteListData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class FavouriteListData(
    val _id: String,
    val userId: String,
    val vocbId: VocbIdData = VocbIdData(
        _id = "",
        title = "",
        vocb = ""
    ),
    val isFavourite: Boolean,
)

data class VocbIdData(
    val _id: String,
    val title: String,
    val vocb: String,
    val img: String = "",
    val meaning: String = "",
    val type: String = "",
    val example: String = "",
    val pronounce: String = "",
)

data class FavouriteCancelResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val status: LoadStatus = LoadStatus.Init()
)

