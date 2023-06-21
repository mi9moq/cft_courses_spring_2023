package com.example.a2023_q2_mironov.di.module

import com.example.a2023_q2_mironov.data.LocalDateTimeGsonAdapter
import com.example.a2023_q2_mironov.data.network.LoanApi
import com.example.a2023_q2_mironov.di.AppScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.time.LocalDateTime

@Module
object NetworkModule {

    private const val BASE_URL = "https://shiftlab.cft.ru:7777/"


    @AppScope
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @AppScope
    fun provideGson(): Gson =
        GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeGsonAdapter)
            .create()

    @AppScope
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @AppScope
    @Provides
    fun provideLoanApi(retrofit: Retrofit): LoanApi =
        retrofit.create()
}