package com.example.a2023_q2_mironov.di.module

import com.example.a2023_q2_mironov.data.datasource.LoanRemoteDataSource
import com.example.a2023_q2_mironov.data.datasource.LoanRemoteDataSourceImpl
import com.example.a2023_q2_mironov.data.repository.LoanRepositoryImpl
import com.example.a2023_q2_mironov.di.AppScope
import com.example.a2023_q2_mironov.domain.repository.LoanRepository
import dagger.Binds
import dagger.Module

@Module
interface LoanModule {

    @AppScope
    @Binds
    fun bindLoanRepository(impl: LoanRepositoryImpl): LoanRepository

    @AppScope
    @Binds
    fun bindLoanRemoteDataSource(impl: LoanRemoteDataSourceImpl): LoanRemoteDataSource

}