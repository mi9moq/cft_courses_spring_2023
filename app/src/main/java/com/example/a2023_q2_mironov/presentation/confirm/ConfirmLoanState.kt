package com.example.a2023_q2_mironov.presentation.confirm

import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType

sealed interface ConfirmLoanState {

    object Initial : ConfirmLoanState

    object Loading : ConfirmLoanState

    data class Content(val loanRequest: LoanRequest) : ConfirmLoanState

    data class Error(val type: LoanErrorType) : ConfirmLoanState
}