package com.example.ck_android.ui.screens.grammar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.GrammarItemResponse
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
class GrammarItemViewModel @Inject constructor(
    private val apiService: ApiService,
    private val mainLog: MainLog,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val _uiState = MutableStateFlow(GrammarItemResponse())
    val uiState = _uiState.asStateFlow()

    suspend fun getToken(): String? {
        return dataStoreManager.getAccessToken().first()
    }

    fun getGrammarBySlug(accessToken: String, slug: String) {
        _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
        try {
            viewModelScope.launch {
                val response =
                    requireNotNull(apiService).getGrammarItem("Bearer $accessToken", slug)
                if (response.statusCode == 200) {
                    _uiState.value =
                        _uiState.value.copy(
                            data = response.data,
                            status = LoadStatus.Success(response.message)
                        )
                    Log.d("getGrammarBySlug Success", "getGrammarBySlug: ${response.data}")
                } else {
                    _uiState.value =
                        _uiState.value.copy(status = LoadStatus.Error(response.message))
                    Log.d("getGrammarBySlug Error 1", "getGrammarBySlug: ${response.data}")
                }
            }
        } catch (ex: Exception) {
            _uiState.value =
                _uiState.value.copy(status = LoadStatus.Error(ex.message.toString()))
            Log.e("getGrammarBySlug Error 2", "getGrammarBySlug: ${ex.message}")

        }
    }
}