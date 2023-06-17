package com.example.a2023_q2_mironov.data.converter

import com.example.a2023_q2_mironov.data.network.model.LoanDto
import com.example.a2023_q2_mironov.domain.entity.Loan
import javax.inject.Inject

class LoanConverter @Inject constructor() {

    fun revert(from: LoanDto): Loan =
        Loan(
            amount = from.amount,
            firstName = from.firstName,
            lastName = from.lastName,
            percent = from.percent,
            period = from.period,
            phoneNumber = from.phoneNumber,
            id = from.id,
            date = from.date,
            status = from.status
        )

    fun convert(from: Loan): LoanDto =
        LoanDto(
            amount = from.amount,
            firstName = from.firstName,
            lastName = from.lastName,
            percent = from.percent,
            period = from.period,
            phoneNumber = from.phoneNumber,
            id = from.id,
            date = from.date,
            status = from.status
        )
}