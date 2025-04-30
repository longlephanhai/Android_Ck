package com.example.ck_android.ui.screens.Writing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.GeminiRequest
import com.example.ck_android.model.GeminiResponse
import com.example.ck_android.model.TitleGeminiResponse
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
class WritingViewModel @Inject constructor(
    private val apiService: ApiService,
    private val mainLog: MainLog,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val _uiState = MutableStateFlow(GeminiResponse())
    val uiState = _uiState.asStateFlow()

    val _titleGemini = MutableStateFlow(TitleGeminiResponse())
    val titleGemini = _titleGemini.asStateFlow()

    suspend fun getToken(): String? {
        return dataStoreManager.getAccessToken().first()
    }

    fun updateTitle(title: String) {
        _titleGemini.value = _titleGemini.value.copy(
            data = _titleGemini.value.data.copy(title = title)
        )
    }

    fun postPrompt(prompt: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val request = GeminiRequest(
                    prompt = prompt,
                )
                val accessToken = dataStoreManager.getAccessToken().first()
                val response =
                    requireNotNull(apiService).postWritten("Bearer $accessToken", request)
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
            } catch (ex: Exception) {
                _uiState.value =
                    _uiState.value.copy(status = LoadStatus.Error(ex.message.toString()))
            }
        }
    }

    fun getTitle() {
        viewModelScope.launch {
            _titleGemini.value = _titleGemini.value.copy(status = LoadStatus.Loading())
            try {
                val accessToken = dataStoreManager.getAccessToken().first()
                val response = requireNotNull(apiService).getTitleWritten("Bearer $accessToken")
                if (response.statusCode == 200) {
                    _titleGemini.value = _titleGemini.value.copy(
                        data = response.data,
                        status = LoadStatus.Success(response.message)
                    )
                } else {
                    _titleGemini.value = _titleGemini.value.copy(
                        status = LoadStatus.Error(response.message)
                    )
                }
            } catch (ex: Exception) {
                _titleGemini.value = _titleGemini.value.copy(
                    status = LoadStatus.Error(ex.message.toString())
                )
            }
        }
    }
}