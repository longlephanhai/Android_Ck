package com.example.ck_android.ui.screens.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.TestDefineRequest
import com.example.ck_android.model.TestDefineResponse
import com.example.ck_android.model.VocabularyCategoryResponse
import com.example.ck_android.repositories.ApiService
import com.example.ck_android.repositories.DataStoreManager
import com.example.ck_android.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestDefineViewModel @Inject constructor(
    private val apiService: ApiService,
    private val mainLog: MainLog,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val _uiState = MutableStateFlow(TestDefineResponse())
    val uiState = _uiState.asStateFlow()

    suspend fun getToken(): String? {
        return dataStoreManager.getAccessToken().first()
    }

    fun getVocabularyRandom(accessToken: String, slug: String, title: String) {
        _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
        try {
            viewModelScope.launch {
                val request = TestDefineRequest(
                    slug = slug,
                    title = title
                )
                val response =
                    requireNotNull(apiService).getVocabularyRandom("Bearer $accessToken", request)
                if (response.statusCode == 201) {
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

}