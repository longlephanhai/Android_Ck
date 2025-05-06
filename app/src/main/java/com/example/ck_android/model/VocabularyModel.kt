package com.example.ck_android.model

import com.example.ck_android.common.enum.LoadStatus

data class VocabularyResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: VocabularyData = VocabularyData(),
    val status: LoadStatus = LoadStatus.Init()
)

data class VocabularyData(
    val titles: List<VocabularyItemData> = emptyList(),
    val category: List<String> = emptyList()
)

data class VocabularyItemData(
    val _id: String = "",
    val slug: String = ""
)

data class VocabularyByTitleResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<String> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class VocabularyCategoryResponse(
    val statusCode: Int = -1,
    val message: String = "",
    val data: List<VocabularyCategoryItemData> = emptyList(),
    val status: LoadStatus = LoadStatus.Init()
)

data class VocabularyCategoryItemData(
    val _id: String = "",
    val title: String = "",
    val vocb: String = "",
    val meaning: String = "",
    val example: String = "",
    val type: String = "",
    val category: String = "",
    val level: String = "",
    val pronounce: String = "",
    val img: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val __v: Int = 0,
    val slug: String = ""
)

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