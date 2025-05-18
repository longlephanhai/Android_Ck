package com.example.ck_android.ui.screens.partsix

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.PartFiveQuestionResponse
import com.example.ck_android.model.PartSixQuestionResponse
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
class PartSixItemViewModel @Inject constructor(
    private val apiService: ApiService,
    private val mainLog: MainLog,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {
    val _uiState = MutableStateFlow(PartSixQuestionResponse())
    val uiState = _uiState.asStateFlow()


    fun getPartSixQuestion(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val accessToken = dataStoreManager.getAccessToken().first()
                val response =
                    requireNotNull(apiService).getQuestionPartSix("Bearer $accessToken", id)
                if (response.statusCode == 200) {
                    _uiState.value = _uiState.value.copy(
                        data = response.data,
                        status = LoadStatus.Success(response.message)
                    )
                } else {
                    _uiState.value =
                        _uiState.value.copy(status = LoadStatus.Error(response.message))
                }
            } catch (ex: Exception) {
                _uiState.value =
                    _uiState.value.copy(status = LoadStatus.Error(ex.message.toString()))
            }
        }
    }
}