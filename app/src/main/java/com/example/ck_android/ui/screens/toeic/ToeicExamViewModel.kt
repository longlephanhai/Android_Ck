package com.example.ck_android.ui.screens.toeic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.QuestionResponse
import com.example.ck_android.model.ToeicExamResponse
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
class ToeicExamViewModel @Inject constructor(
    private val apiService: ApiService,
    private val mainLog: MainLog,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val _question = MutableStateFlow(QuestionResponse())
    val question = _question.asStateFlow()

    val _toeicExam = MutableStateFlow(ToeicExamResponse())
    val toeicExam = _toeicExam.asStateFlow()

    suspend fun getToken(): String? {
        return dataStoreManager.getAccessToken().first()
    }

    fun getToeicExam(accessToken: String, id: String) {
        viewModelScope.launch {
            _toeicExam.value = _toeicExam.value.copy(status = LoadStatus.Loading())
            try {
                val response = requireNotNull(apiService).getToeicExam("Bearer $accessToken", id)
                if (response.statusCode == 200) {
                    _toeicExam.value = _toeicExam.value.copy(
                        data = response.data,
                        status = LoadStatus.Success(response.message)
                    )
                } else {
                    _toeicExam.value =
                        _toeicExam.value.copy(
                            status = LoadStatus.Error(response.message)
                        )
                }
            } catch (ex: Exception) {
                _toeicExam.value =
                    _toeicExam.value.copy(status = LoadStatus.Error(ex.message.toString()))
            }
        }
    }

    fun getQuestionToeic(accessToken: String, id: String) {
        viewModelScope.launch {
            _question.value = _question.value.copy(status = LoadStatus.Loading())
            try {
                val response =
                    requireNotNull(apiService).getQuestionToeic("Bearer $accessToken", id)
                if (response.statusCode == 200) {
                    _question.value = _question.value.copy(
                        data = response.data,
                        status = LoadStatus.Success(response.message)
                    )
                } else {
                    _question.value =
                        _question.value.copy(
                            status = LoadStatus.Error(response.message)
                        )
                }
            } catch (ex: Exception) {
                _question.value =
                    _question.value.copy(status = LoadStatus.Error(ex.message.toString()))
            }
        }
    }

}