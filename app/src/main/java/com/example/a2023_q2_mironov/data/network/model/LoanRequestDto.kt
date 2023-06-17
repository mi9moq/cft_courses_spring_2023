package com.example.a2023_q2_mironov.data.network.model

data class LoanRequestDto(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String
)
