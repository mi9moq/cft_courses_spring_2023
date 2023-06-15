package com.example.a2023_q2_mironov.di

import com.example.a2023_q2_mironov.data.datasource.AuthRemoteDataSource
import com.example.a2023_q2_mironov.data.datasource.AuthRemoteDataSourceImpl
import com.example.a2023_q2_mironov.data.network.LoanApi
import com.example.a2023_q2_mironov.data.repository.AuthRepositoryImpl
import com.example.a2023_q2_mironov.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
interface DataModule {

    @AppScope
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @AppScope
    @Binds
    fun bindRemoteDataSource(impl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    companion object {

        private const val BASE_URL = "https://shiftlab.cft.ru:7777/"

        @AppScope
        @Provides
        fun provideHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

        @AppScope
        @Provides
        fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        @AppScope
        @Provides
        fun provideAnimeApi(retrofit: Retrofit): LoanApi =
            retrofit.create()
    }
}