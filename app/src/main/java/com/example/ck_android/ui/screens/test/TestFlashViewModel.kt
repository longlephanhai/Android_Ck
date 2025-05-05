package com.example.ck_android.ui.screens.test

import androidx.lifecycle.ViewModel
import com.example.ck_android.repositories.ApiService
import com.example.ck_android.repositories.DataStoreManager
import com.example.ck_android.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestFlashViewModel @Inject constructor(
    private val apiService: ApiService,
    private val mainLog: MainLog,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
}