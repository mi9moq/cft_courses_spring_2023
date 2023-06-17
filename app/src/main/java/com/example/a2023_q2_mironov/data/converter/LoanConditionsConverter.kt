package com.example.a2023_q2_mironov.data.converter

import com.example.a2023_q2_mironov.data.network.model.LoanConditionsDto
import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import javax.inject.Inject

class LoanConditionsConverter @Inject constructor() {

    fun convert(from: LoanConditions): LoanConditionsDto =
        LoanConditionsDto(
            maxAmount = from.maxAmount,
            percent = from.percent,
            period = from.period
        )

    fun revert(from: LoanConditionsDto): LoanConditions =
        LoanConditions(
            maxAmount = from.maxAmount,
            percent = from.percent,
            period = from.period
        )
}