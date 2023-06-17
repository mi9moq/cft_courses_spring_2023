package com.example.a2023_q2_mironov.data.network

import com.example.a2023_q2_mironov.data.network.model.AuthDto
import com.example.a2023_q2_mironov.data.network.model.LoanConditionsDto
import com.example.a2023_q2_mironov.data.network.model.LoanDto
import com.example.a2023_q2_mironov.data.network.model.UserDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface LoanApi {

    @POST("registration")
    suspend fun registration(
        @Body authDto: AuthDto
    ): UserDto

    @POST("login")
    suspend fun login(
        @Body authDto: AuthDto
    ): Response<ResponseBody>

    @GET("/loans/all")
    suspend fun getAll(@Header("Authorization") token: String): List<LoanDto>

    @GET("/loans/{id}")
    suspend fun getLoanById(
        @Header("Authorization") token: String,
        @Path("id") loanId: Long
    ): LoanDto

    @GET("/loans/conditions")
    suspend fun getLoanConditions(@Header("Authorization") token: String): LoanConditionsDto
}