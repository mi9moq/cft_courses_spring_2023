package com.example.a2023_q2_mironov.utils

import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import com.example.a2023_q2_mironov.domain.entity.LoanStatus
import java.time.LocalDateTime

object LoanData {

    val loanEntity = Loan(
        amount = 100,
        firstName = "First",
        lastName = "Last",
        percent = 5.0,
        period = 12,
        phoneNumber = "88005353535",
        id = 111L,
        date = LocalDateTime.now(),
        status = LoanStatus.APPROVED
    )

    val loanRequestEntity = LoanRequest(
        amount = 100,
        firstName = "First",
        lastName = "Last",
        period = 6,
        percent = 6.6,
        phoneNumber = "88005353535"
    )

    val loanConditionsEntity = LoanConditions(
        maxAmount = 1000,
        percent = 6.6,
        period = 12
    )

    val loansList: List<Loan> = listOf(loanEntity)
}