package com.example.a2023_q2_mironov.utils

import com.example.a2023_q2_mironov.data.network.model.LoanConditionsDto
import com.example.a2023_q2_mironov.data.network.model.LoanDto
import com.example.a2023_q2_mironov.data.network.model.LoanRequestDto
import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import com.example.a2023_q2_mironov.domain.entity.LoanStatus
import org.mockito.kotlin.mock
import java.time.LocalDateTime

object LoanData {

    private val date: LocalDateTime = mock()

    val loanEntity = Loan(
        amount = 100,
        firstName = "First",
        lastName = "Last",
        percent = 5.0,
        period = 12,
        phoneNumber = "88005353535",
        id = 111L,
        date = date,
        status = LoanStatus.APPROVED
    )

    val loanDto = LoanDto(
        amount = 100,
        firstName = "First",
        lastName = "Last",
        percent = 5.0,
        period = 12,
        phoneNumber = "88005353535",
        id = 111L,
        date = date,
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

    val loanRequestDto = LoanRequestDto(
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

    val loanConditionsDto = LoanConditionsDto(
        maxAmount = 1000,
        percent = 6.6,
        period = 12
    )

    val loansList: List<Loan> = listOf(loanEntity)
}