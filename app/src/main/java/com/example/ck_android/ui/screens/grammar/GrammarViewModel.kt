package com.example.ck_android.ui.screens.grammar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.GrammarResponse
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
class GrammarViewModel @Inject constructor(
    private val apiService: ApiService,
    private val mainLog: MainLog,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val _uiState = MutableStateFlow(GrammarResponse())
    val uiState = _uiState.asStateFlow()

    suspend fun getToken(): String? {
        return dataStoreManager.getAccessToken().first()
    }

    fun getGetAllGrammar(accessToken: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val response = requireNotNull(apiService).getGrammars("Bearer $accessToken")
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