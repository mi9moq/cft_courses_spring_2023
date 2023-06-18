package com.example.a2023_q2_mironov.data.network.model

import com.google.gson.annotations.SerializedName

data class LoanDto(
    val amount: Long,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val id: Long,
    val date: String,
    @SerializedName("state")
    val status: String
)
