package com.example.a2023_q2_mironov.data.converter

import com.example.a2023_q2_mironov.data.network.model.LoanRequestDto
import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import javax.inject.Inject

class LoanRequestConverter @Inject constructor() {

    fun convert(from: LoanRequest): LoanRequestDto =
        LoanRequestDto(
            amount = from.amount,
            firstName = from.firstName,
            lastName = from.lastName,
            period = from.period,
            percent = from.percent,
            phoneNumber = from.phoneNumber
        )
}