package com.example.ck_android.ui.screens.register

import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ck_android.common.enum.LoadStatus
import com.example.ck_android.model.RegisterRequest
import com.example.ck_android.repositories.ApiService
import com.example.ck_android.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val application: Application,
    private val apiService: ApiService,
    private val mainLog: MainLog,
) : ViewModel() {
    val _uiState = MutableStateFlow(RegisterRequest())
    val uiState = _uiState.asStateFlow()
    fun updateName(name: String) {
        _uiState.value = uiState.value.copy(name = name)
    }

    fun updateEmail(email: String) {
        _uiState.value = uiState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _uiState.value = uiState.value.copy(password = password)
    }

    fun updatePhone(phone: String) {
        _uiState.value = uiState.value.copy(phone = phone)
    }

    fun updateAvatar(avatar: Uri?) {
        _uiState.value = uiState.value.copy(avatar = avatar)
    }


    fun reset() {
        _uiState.value = _uiState.value.copy(status = LoadStatus.Init())
    }


    fun register() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val nameRequestBody =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), _uiState.value.name)
                val emailRequestBody =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), _uiState.value.email)
                val passwordRequestBody =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), _uiState.value.password)
                val phoneRequestBody =
                    RequestBody.create("text/plain".toMediaTypeOrNull(), _uiState.value.phone)

                val avatarPart = _uiState.value.avatar?.let {
                    prepareFilePart("avatar", it)
                }

                val response = apiService.register(
                    avatar = avatarPart,
                    name = nameRequestBody,
                    email = emailRequestBody,
                    password = passwordRequestBody,
                    phone = phoneRequestBody,
                )

                if (response.statusCode == 201) {
                    _uiState.value =
                        _uiState.value.copy(status = LoadStatus.Success("Registration Successful"))
                    val userId = response.data._id.toString()
                    _uiState.value = _uiState.value.copy(
                        status = LoadStatus.Success(response.message),
                        _id = userId // Cập nhật _id
                    )
                } else {
                    _uiState.value =
                        _uiState.value.copy(status = LoadStatus.Error("Registration Failed"))
                }
            } catch (ex: Exception) {
                _uiState.value =
                    _uiState.value.copy(status = LoadStatus.Error(ex.message.toString()))
            }
        }
    }

    private fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part {
        val file =
            File(getRealPathFromURI(fileUri))
        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestBody)
    }

    fun getRealPathFromURI(contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = application.contentResolver.query(contentUri, proj, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        val filePath = cursor?.getString(columnIndex ?: 0)
        cursor?.close()
        return filePath ?: ""
    }
}