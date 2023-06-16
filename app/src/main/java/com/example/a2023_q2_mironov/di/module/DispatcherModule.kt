package com.example.a2023_q2_mironov.di.module

import com.example.a2023_q2_mironov.di.AppScope
import com.example.a2023_q2_mironov.di.IoDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object DispatcherModule {

    @IoDispatcher
    @AppScope
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}