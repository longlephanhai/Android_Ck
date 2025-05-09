package com.example.ck_android.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.FavouriteCancelResponse
import com.example.ck_android.model.FavouriteListResponse
import com.example.ck_android.model.FavouriteRequest
import com.example.ck_android.model.FavouriteResponse
import com.example.ck_android.model.VocabularyCategoryResponse
import com.example.ck_android.repositories.ApiService
import com.example.ck_android.repositories.DataStoreManager
import com.example.ck_android.repositories.MainLog
import com.example.ck_android.ui.screens.vocabulary.VocabularyCategoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainLog: MainLog,
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {
    val _uiState = MutableStateFlow(VocabularyCategoryResponse())
    val uiState = _uiState.asStateFlow()

    val _favourite = MutableStateFlow(FavouriteResponse())
    val favourite = _favourite.asStateFlow()

    val _cancelFavourite = MutableStateFlow(FavouriteCancelResponse())
    val cancelFavourite = _cancelFavourite.asStateFlow()

    val _favouriteList = MutableStateFlow(FavouriteListResponse())
    val favouriteList = _favouriteList.asStateFlow()

    suspend fun getToken(): String? {
        return dataStoreManager.getAccessToken().first()
    }

    fun getVocabularyCategory(accessToken: String, slug: String, category: String) {
        _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
        try {
            viewModelScope.launch {
                val response = requireNotNull(apiService).getVocabularyCategory(
                    "Bearer $accessToken",
                    slug,
                    category
                )
                if (response.statusCode == 200) {
                    _uiState.value = _uiState.value.copy(
                        data = response.data,
                        status = LoadStatus.Success(response.message)
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        status = LoadStatus.Error(response.message)
                    )
                }
            }
        } catch (ex: Exception) {
            _uiState.value =
                _uiState.value.copy(status = LoadStatus.Error(ex.message.toString()))
        }
    }

    fun postFavourite(vocbId: String) {
        _favourite.value = _favourite.value.copy(status = LoadStatus.Loading())
        try {
            viewModelScope.launch {
                val accessToken = dataStoreManager.getAccessToken().first()
                val request = FavouriteRequest(vocbId = vocbId)
                val response =
                    requireNotNull(apiService).postFavouriteVocb("Bearer $accessToken", request)
                if (response.statusCode == 201) {
                    _favourite.value = _favourite.value.copy(
                        data = response.data,
                        status = LoadStatus.Success(response.message)
                    )
                    getFavouriteVocbList()
                } else {
                    _favourite.value = _favourite.value.copy(
                        status = LoadStatus.Error(response.message)
                    )
                }
            }
        } catch (ex: Exception) {
            _favourite.value =
                _favourite.value.copy(status = LoadStatus.Error(ex.message.toString()))
        }
    }

    fun getFavouriteVocbList() {
        _favouriteList.value = _favouriteList.value.copy(status = LoadStatus.Loading())
        try {
            viewModelScope.launch {
                val accessToken = dataStoreManager.getAccessToken().first()
                val response = requireNotNull(apiService).getFavouriteList("Bearer $accessToken")
                if (response.statusCode == 200) {
                    _favouriteList.value = _favouriteList.value.copy(
                        data = response.data,
                        status = LoadStatus.Success(response.message)
                    )
//                    Log.d("Debug", "Favourite list: ${response.data}")
                } else {
                    _favouriteList.value = _favouriteList.value.copy(
                        status = LoadStatus.Error(response.message)
                    )
                }
            }
        } catch (ex: Exception) {
            _favouriteList.value =
                _favouriteList.value.copy(status = LoadStatus.Error(ex.message.toString()))
        }
    }

    fun cancelFavouriteVocb(vocbId: String) {
        _cancelFavourite.value = _cancelFavourite.value.copy(status = LoadStatus.Loading())
        try {
            viewModelScope.launch {
                val accessToken = dataStoreManager.getAccessToken().first()
                val response =
                    requireNotNull(apiService).deleteFavouriteVocb("Bearer $accessToken", vocbId)
                if (response.statusCode == 201) {
                    _cancelFavourite.value = _cancelFavourite.value.copy(
                        status = LoadStatus.Success(response.message)
                    )
                    getFavouriteVocbList()
                } else {
                    _cancelFavourite.value = _cancelFavourite.value.copy(
                        status = LoadStatus.Error(response.message)
                    )
                }
            }
        } catch (ex: Exception) {
            _cancelFavourite.value =
                _cancelFavourite.value.copy(status = LoadStatus.Error(ex.message.toString()))
        }
    }
}