package com.example.ck_android.ui.screens.speakAI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ck_android.model.ChatRequest
import com.example.ck_android.repositories.ApiService
import com.example.ck_android.repositories.DataStoreManager
import com.example.ck_android.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeakAIViewModel @Inject constructor(
    private val apiService: ApiService,
    private val mainLog: MainLog,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _text = MutableStateFlow("")
    val text = _text.asStateFlow()

    private val _aiResponse = MutableStateFlow("")
    val aiResponse = _aiResponse.asStateFlow()

    private val _audio = MutableStateFlow<ByteArray?>(null)
    val audio = _audio.asStateFlow()

    fun updateText(newText: String) {
        _text.value = newText
    }

    fun startConversation() {
        viewModelScope.launch {
            val token = dataStoreManager.getAccessToken().firstOrNull() ?: return@launch
            val res = apiService.chatGpt("Bearer $token", ChatRequest(""))
            _aiResponse.value = res.text
            _audio.value = decodeBase64Audio(res.audio)
        }
    }

    fun sendUserReply() {
        viewModelScope.launch {
            val token = dataStoreManager.getAccessToken().firstOrNull() ?: return@launch
            val res = apiService.chatGpt("Bearer $token", ChatRequest(_text.value))
            _aiResponse.value = res.text
            _audio.value = decodeBase64Audio(res.audio)
            _text.value = ""
        }
    }

    private fun decodeBase64Audio(base64: String): ByteArray {
        val cleanBase64 = base64.substringAfter(",")
        return android.util.Base64.decode(cleanBase64, android.util.Base64.DEFAULT)
    }




}