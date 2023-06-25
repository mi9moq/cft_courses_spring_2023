package com.example.a2023_q2_mironov.presentation.create

import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType

sealed interface CreateLoanState {

    object Initial : CreateLoanState

    object Loading : CreateLoanState

    data class Content(val condition: LoanConditions) : CreateLoanState

    data class Error(val type: LoanErrorType) : CreateLoanState
}