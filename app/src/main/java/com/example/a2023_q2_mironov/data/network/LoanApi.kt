package com.example.a2023_q2_mironov.data.network

import com.example.a2023_q2_mironov.data.network.model.AuthDto
import com.example.a2023_q2_mironov.data.network.model.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface LoanApi {

    @POST("registration")
    suspend fun registration(
        @Body authDto: AuthDto
    ): UserDto

    @POST("login")
    suspend fun login(
        @Body authDto: AuthDto
    ): String
}