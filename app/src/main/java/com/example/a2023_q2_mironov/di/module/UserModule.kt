package com.example.a2023_q2_mironov.di.module

import com.example.a2023_q2_mironov.data.datasource.TokenLocalDataSource
import com.example.a2023_q2_mironov.data.datasource.TokenLocalDataSourceImpl
import com.example.a2023_q2_mironov.data.repository.UserTokenRepositoryImpl
import com.example.a2023_q2_mironov.di.AppScope
import com.example.a2023_q2_mironov.domain.repository.UserTokenRepository
import dagger.Binds
import dagger.Module

@Module
interface UserModule {

    @AppScope
    @Binds
    fun bindUserTokenRepository(impl: UserTokenRepositoryImpl): UserTokenRepository

    @AppScope
    @Binds
    fun bindTokenLocalDataSource(impl: TokenLocalDataSourceImpl): TokenLocalDataSource
}