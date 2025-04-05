package com.example.ck_android.di

import com.example.ck_android.repositories.ApiService
import com.example.ck_android.repositories.ApiServiceImpl
import com.example.ck_android.repositories.MainLog
import com.example.ck_android.repositories.MainLogImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun bindMainLog(
        log: MainLogImpl
    ): MainLog

    @Binds
    @Singleton
    abstract fun bindApi(
        apiServiceImpl: ApiServiceImpl
    ): ApiService

}