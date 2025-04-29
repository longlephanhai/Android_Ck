package com.example.ck_android.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.LoginRequest
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
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val mainLog: MainLog,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val _uiState = MutableStateFlow(LoginRequest())
    val uiState = _uiState.asStateFlow()
    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun reset() {
        _uiState.value = _uiState.value.copy(status = LoadStatus.Init())
    }

    // Hàm lưu access_token sau khi đăng nhập thành công
    fun saveToken(token: String) {
        viewModelScope.launch {
            dataStoreManager.saveAccessToken(token)
        }
    }

    // Hàm lưu người dùng
    fun saveUser(id: String, name: String, email: String) {
        viewModelScope.launch {
            dataStoreManager.saveUser(id, name, email)
        }
    }

    // Hàm lấy access_token
    suspend fun getToken(): String? {
        return dataStoreManager.getAccessToken().first()
    }

    // Hàm đăng xuất (xoá token)
    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearAccessToken()
        }
    }

    fun login() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val loginRequest =
                    LoginRequest(email = _uiState.value.email, password = _uiState.value.password)
                val response = requireNotNull(apiService).login(loginRequest)
                if (response.statusCode == 201) {
                    // CHỜ lưu token và user info
                    saveToken(response.data.access_token)
                    saveUser(
                        response.data.user.id,
                        response.data.user.name,
                        response.data.user.email
                    )
                    _uiState.value =
                        _uiState.value.copy(status = LoadStatus.Success(response.message))
                    Log.d("Response Success", "Success: $response")
                } else {
                    _uiState.value =
                        _uiState.value.copy(status = LoadStatus.Error(response.message))
                    Log.d("Response Error", "Error: $response")
                }
            } catch (ex: Exception) {
                _uiState.value =
                    _uiState.value.copy(status = LoadStatus.Error(ex.message.toString()))
                Log.e("Response Error", "Error: $ex")
            }
        }
    }

}