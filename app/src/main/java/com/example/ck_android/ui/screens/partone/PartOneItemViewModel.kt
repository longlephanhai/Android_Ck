package com.example.ck_android.ui.screens.partone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.PartOneQuestionResponse
import com.example.ck_android.model.ScoreRequest
import com.example.ck_android.model.ScoreResponse
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
class PartOneItemViewModel @Inject constructor(
    private val apiService: ApiService,
    private val mainLog: MainLog,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {
    val _uiState = MutableStateFlow(PartOneQuestionResponse())
    val uiState = _uiState.asStateFlow()

    val _scoreExam = MutableStateFlow(ScoreResponse())
    val scoreExam = _scoreExam.asStateFlow()

    fun getPartOneQuestion(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val accessToken = dataStoreManager.getAccessToken().first()
                val response =
                    requireNotNull(apiService).getQuestionPartOne("Bearer $accessToken", id)
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

    fun postScoreToeic(
        score: Int,
        examId: String,
        correctAnswers: String,
        incorrectAnswers: String
    ) {
        viewModelScope.launch {
            _scoreExam.value = _scoreExam.value.copy(status = LoadStatus.Loading())
            try {
                val (userId, _, _) = dataStoreManager.getUser().first()

                val dataRequest = ScoreRequest(
                    score = score,
                    userId = userId.toString(),
                    examId = examId,
                    correctAnswers = correctAnswers,
                    incorrectAnswers = incorrectAnswers
                )
                val accessToken = requireNotNull(dataStoreManager).getAccessToken().first()
                val response =
                    requireNotNull(apiService).postScore("Bearer $accessToken", dataRequest)
                if (response.statusCode == 201) {
                    _scoreExam.value = _scoreExam.value.copy(
                        status = LoadStatus.Success(response.message)
                    )
                } else {
                    _scoreExam.value =
                        _scoreExam.value.copy(
                            status = LoadStatus.Error(response.message)
                        )
                }
            } catch (ex: Exception) {
                _scoreExam.value =
                    _scoreExam.value.copy(status = LoadStatus.Error(ex.message.toString()))

            }
        }
    }
}
