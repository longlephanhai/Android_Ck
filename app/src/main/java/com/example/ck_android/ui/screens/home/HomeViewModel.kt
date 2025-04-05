package com.example.ck_android.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.ck_android.repositories.ApiService
import com.example.ck_android.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainLog: MainLog,
    private val apiService: ApiService
) : ViewModel() {
}