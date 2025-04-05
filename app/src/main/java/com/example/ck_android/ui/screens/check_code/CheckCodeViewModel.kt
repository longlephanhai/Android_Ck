package com.example.ck_android.ui.screens.check_code

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.CheckCodeRequest
import com.example.ck_android.repositories.ApiService
import com.example.ck_android.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckCodeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val mainLog: MainLog
) : ViewModel() {
    val _uiState = MutableStateFlow(CheckCodeRequest())
    val uiState = _uiState.asStateFlow()

    fun updateCode(code: String) {
        _uiState.value = _uiState.value.copy(code = code)
    }

    fun reset() {
        _uiState.value = _uiState.value.copy(status = LoadStatus.Init())
    }

    fun checkCode(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val checkCodeRequest =
                    CheckCodeRequest(id, code = _uiState.value.code)
                val response = requireNotNull(apiService).checkCode(checkCodeRequest)
                if (response.statusCode == 201) {
                    _uiState.value =
                        _uiState.value.copy(status = LoadStatus.Success(response.message))
                } else {
                    _uiState.value =
                        _uiState.value.copy(status = LoadStatus.Error(response.message))
                }
            } catch (ex: Exception) {
                _uiState.value =
                    _uiState.value.copy(status = LoadStatus.Error(ex.message.toString()))
                Log.e("Response Error", "Error: $ex")
            }
        }
    }
}