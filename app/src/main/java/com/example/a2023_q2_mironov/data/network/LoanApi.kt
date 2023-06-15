package com.example.a2023_q2_mironov.data.network

import com.example.a2023_q2_mironov.data.network.model.UserDto
import retrofit2.http.POST
import retrofit2.http.Query

interface LoanApi {

    @POST("registration")
    suspend fun registration(
        @Query("name") userName: String,
        @Query("password") password: String
    ): UserDto

    @POST("login")
    suspend fun login(
        @Query("name") userName: String,
        @Query("password") password: String
    ): String
}