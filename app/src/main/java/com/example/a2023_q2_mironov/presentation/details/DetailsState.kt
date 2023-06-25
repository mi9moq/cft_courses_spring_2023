package com.example.a2023_q2_mironov.presentation.details

import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType

sealed interface DetailsState {

    object Initial: DetailsState

    object Loading: DetailsState

    data class Content(val loan: Loan) : DetailsState

    data class Error(val type: LoanErrorType): DetailsState
}