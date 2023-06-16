package com.example.a2023_q2_mironov.di.module

import com.example.a2023_q2_mironov.data.datasource.AuthRemoteDataSource
import com.example.a2023_q2_mironov.data.datasource.AuthRemoteDataSourceImpl
import com.example.a2023_q2_mironov.data.repository.AuthRepositoryImpl
import com.example.a2023_q2_mironov.di.AppScope
import com.example.a2023_q2_mironov.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module

@Module
interface AuthModule {

    @AppScope
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @AppScope
    @Binds
    fun bindAuthRemoteDataSource(impl: AuthRemoteDataSourceImpl): AuthRemoteDataSource
}