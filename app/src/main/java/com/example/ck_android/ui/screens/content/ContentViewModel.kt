package com.example.ck_android.ui.screens.content

import androidx.lifecycle.ViewModel
import com.example.ck_android.repositories.ApiService
import com.example.ck_android.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val apiService: ApiService,
    private val mainLog: MainLog
) : ViewModel() {
}